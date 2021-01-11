package com.jkpark.study.security.token;

import com.jkpark.study.global.dto.AuthenticationDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

	private PreAuthorizationToken(String username, String password) {
		super(username, password);
	}

	public PreAuthorizationToken(AuthenticationDTO dto) {
		this(dto.getId(), dto.getPw());
	}

	public String getUsername() {
		return (String)super.getPrincipal();
	}

	public String getUserPassword() {
		return (String)super.getCredentials();
	}
}
