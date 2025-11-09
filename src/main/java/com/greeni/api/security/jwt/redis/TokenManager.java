package com.greeni.api.security.jwt.redis;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.greeni.api.security.jwt.enums.RedisTokenType;
import com.greeni.api.security.jwt.provider.JwtProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenManager {

	private final JwtProvider jwtProvider;
	private final StringRedisTemplate stringRedisTemplate;

	private String getTokenPrefix(RedisTokenType tokenType, String email) {
		return tokenType.name() + ":" + email;
	}

	public void saveToken(RedisTokenType tokenType, String email, String token) {
		long expiredMS = jwtProvider.getExpiredMS(token);
		long nowMS = System.currentTimeMillis();
		long expired = nowMS + expiredMS;

		String key = getTokenPrefix(tokenType, email);
		stringRedisTemplate.opsForValue().set(key, token, Duration.ofMillis(expired));
	}

	public boolean findToken(RedisTokenType tokenType, String email) {
		String key = getTokenPrefix(tokenType, email);
		return stringRedisTemplate.hasKey(key);
	}

	public String getToken(RedisTokenType tokenType, String email) {
		String key = getTokenPrefix(tokenType, email);
		return stringRedisTemplate.opsForValue().get(key);
	}

	public void removeToken(RedisTokenType tokenType, String email) {
		String key = getTokenPrefix(tokenType, email);
		stringRedisTemplate.delete(key);
	}
}
