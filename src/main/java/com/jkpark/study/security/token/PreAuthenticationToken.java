package com.jkpark.study.security.token;

import com.jkpark.study.account.dto.AccountDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private PreAuthenticationToken(String username, String password) {
		super(username, password);
	}

	public PreAuthenticationToken(AccountDTO dto) {
		this(dto.getId(), dto.getPw());
	}

	public String getUsername() {
		return (String)super.getPrincipal();
	}

	public String getUserPassword() {
		return (String)super.getCredentials();
	}
}
