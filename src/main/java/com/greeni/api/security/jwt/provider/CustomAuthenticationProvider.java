package com.greeni.api.security.jwt.provider;

import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.greeni.api.security.jwt.token.CustomAuthenticationToken;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;
import com.greeni.api.security.jwt.userDetails.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final JwtProvider jwtProvider;
	private final CustomUserDetailsService customUserDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = authentication.getDetails().toString();
		String subject = jwtProvider.getSubject(token);

		CustomUserDetails userDetails = (CustomUserDetails)customUserDetailsService.loadUserByUsername(subject);
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

		return new CustomAuthenticationToken(userDetails, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return CustomAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
