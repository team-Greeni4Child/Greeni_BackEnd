package com.greeni.api.security.jwt.token;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter
public class CustomAuthenticationToken extends AbstractAuthenticationToken {

	public CustomAuthenticationToken(Object principal) {
		super(null);
		super.setDetails(principal);
		setAuthenticated(false);
	}

	public CustomAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		super.setDetails(principal);
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return super.getDetails();
	}
}
