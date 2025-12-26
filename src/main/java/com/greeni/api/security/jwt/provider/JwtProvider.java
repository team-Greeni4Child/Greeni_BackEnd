package com.greeni.api.security.jwt.provider;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.JwtErrorStatus;
import com.greeni.api.security.jwt.dto.JwtProperties;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {

	private final Long accessTokenExpiredMS;
	private final Long refreshTokenExpiredMS;
	private final Key signingKey;

	public JwtProvider(JwtProperties jwtProperties) {
		this.accessTokenExpiredMS = jwtProperties.accessTokenExpiration().toMillis();
		this.refreshTokenExpiredMS = jwtProperties.refreshTokenExpiration().toMillis();
		this.signingKey = Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes());
	}

	private String createToken(
		String subject, Long memberId, Collection<? extends GrantedAuthority> authorities, Long expiredMS) {
		Claims claims = Jwts.claims().setSubject(subject);
		claims.put("memberId", memberId);
		if (authorities != null) {
			claims.put("authorities",
				authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		}

		Date now = new Date();
		Date expired = new Date(now.getTime() + expiredMS);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expired)
			.signWith(signingKey)
			.compact();
	}

	public String createAccessToken(Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		String subject = userDetails.getUsername();
		Long memberId = userDetails.getId();
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

		return createToken(subject, memberId, authorities, accessTokenExpiredMS);
	}

	public String createRefreshToken(Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		String subject = userDetails.getUsername();
		Long memberId = userDetails.getId();

		return createToken(subject, memberId, null, refreshTokenExpiredMS);
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		log.debug("JWT Resolving Failed: {}", bearerToken);
		return null;
	}

	public Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(signingKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token: {}", e.getMessage());
			throw new GeneralException(JwtErrorStatus.EXPIRED_TOKEN);
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token: {}", e.getMessage());
			throw new GeneralException(JwtErrorStatus.UNSUPPORTED_TOKEN);
		} catch (MalformedJwtException e) {
			log.error("Malformed JWT token: {}", e.getMessage());
			throw new GeneralException(JwtErrorStatus.MALFORMED_TOKEN);
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty or null: {}", e.getMessage());
			throw new GeneralException(JwtErrorStatus.TOKEN_NOT_FOUND);
		} catch (SignatureException e) {
			log.error("Wrong JWT Signature: {}", e.getMessage());
			throw new GeneralException(JwtErrorStatus.WRONG_SIGNATURE);
		} catch (JwtException e) {
			log.error("Unhandled JWT exception: {}", e.getMessage());
			throw new GeneralException(JwtErrorStatus.INVALID_TOKEN);
		}
	}

	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}

	public Long getExpiredMS(String token) {
		return parseClaims(token).getExpiration().getTime();
	}
}
