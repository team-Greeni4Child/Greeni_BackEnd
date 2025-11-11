package com.greeni.api.security.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greeni.api.apiPayload.exception.GeneralException;
import com.greeni.api.apiPayload.status.JwtErrorStatus;
import com.greeni.api.apiPayload.status.MemberErrorStatus;
import com.greeni.api.members.domain.Member;
import com.greeni.api.members.repository.MemberRepository;
import com.greeni.api.security.auth.converter.AuthConverter;
import com.greeni.api.security.auth.dto.AuthRequestDTO;
import com.greeni.api.security.auth.dto.AuthResponseDTO;
import com.greeni.api.security.jwt.enums.RedisTokenType;
import com.greeni.api.security.jwt.provider.JwtProvider;
import com.greeni.api.security.jwt.redis.TokenManager;
import com.greeni.api.security.jwt.token.CustomAuthenticationToken;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

	private final MemberRepository memberRepository;
	private final TokenManager tokenManager;
	private final JwtProvider jwtProvider;
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	private AuthResponseDTO.LoginResult generateAuthResponse(
		Authentication auth, HttpServletResponse response) {

		String email = ((CustomUserDetails)auth.getPrincipal()).getEmail();
		Long memberId = ((CustomUserDetails)auth.getPrincipal()).getId();
		String accessToken = jwtProvider.createAccessToken(auth);
		String refreshToken = jwtProvider.createRefreshToken(auth);

		tokenManager.saveToken(RedisTokenType.REFRESH_TOKEN, email, refreshToken);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Authorization", "Bearer " + accessToken);

		return AuthConverter.toLoginResult(refreshToken, memberId);
	}

	@Override
	public AuthResponseDTO.LoginResult login(AuthRequestDTO.Login loginRequest, HttpServletResponse response) {

		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		UserDetails userDetails;

		try {
			userDetails = userDetailsService.loadUserByUsername(email);
		} catch (UsernameNotFoundException e) {
			throw new GeneralException(MemberErrorStatus.NOT_EXIST_EMAIL);
		}

		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new GeneralException(MemberErrorStatus.PASSWORD_NOT_MATCH);
		}

		if (tokenManager.findToken(RedisTokenType.LOGOUT_ACCESS_TOKEN, email)) {
			tokenManager.removeToken(RedisTokenType.LOGOUT_ACCESS_TOKEN, email);
		}

		Authentication auth = new CustomAuthenticationToken(userDetails, null);
		SecurityContextHolder.getContext().setAuthentication(auth);

		return generateAuthResponse(auth, response);
	}

	@Override
	public AuthResponseDTO.LoginResult reissue(String refreshToken, HttpServletResponse response) {

		String email = jwtProvider.getSubject(refreshToken);

		if (refreshToken == null || refreshToken.isEmpty() || !tokenManager.findToken(RedisTokenType.REFRESH_TOKEN,
			email)) {
			throw new GeneralException(JwtErrorStatus.REFRESH_TOKEN_NOT_FOUND);
		}

		UserDetails userDetails;

		try {
			userDetails = userDetailsService.loadUserByUsername(email);
		} catch (UsernameNotFoundException e) {
			log.debug("사용자 확인 불가: {}", e.getMessage());
			throw new GeneralException(MemberErrorStatus.NOT_EXIST_EMAIL);
		} catch (Exception e) {
			log.debug("토큰 재발급 중 에러 발생: {}", e.getMessage());
			throw new GeneralException(JwtErrorStatus.LOGIN_UNKNOWN_ERROR);
		}

		Authentication auth = new CustomAuthenticationToken(userDetails, null);

		return generateAuthResponse(auth, response);
	}

	@Override
	public Member logout(HttpServletRequest request) {

		String accessToken = jwtProvider.resolveToken(request);
		String email = jwtProvider.getSubject(accessToken);

		tokenManager.saveToken(RedisTokenType.LOGOUT_ACCESS_TOKEN, email, accessToken);
		if (tokenManager.findToken(RedisTokenType.REFRESH_TOKEN, email)) {
			tokenManager.removeToken(RedisTokenType.REFRESH_TOKEN, email);
		}

		return memberRepository.findByEmail(email)
			.orElseThrow(() -> new GeneralException(MemberErrorStatus.NOT_EXIST_EMAIL));
	}

	@Override
	public void deleteMember(HttpServletRequest request) {

		Member member = logout(request);
		memberRepository.deleteById(member.getId());
	}
}
