package com.jkpark.study.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenDTO {

	private String authenticationToken;

	private String refreshToken;

	private String id;

	public TokenDTO(String authenticationToken, String refreshToken, String id) {
		this.authenticationToken = authenticationToken;
		this.refreshToken = refreshToken;
		this.id = id;
	}
}
