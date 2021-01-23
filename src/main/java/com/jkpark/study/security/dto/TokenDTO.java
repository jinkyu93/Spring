package com.jkpark.study.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenDTO {

	private String accessToken;

	private String refreshToken;

	private String id;

	public TokenDTO(String accessToken, String refreshToken, String id) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.id = id;
	}
}
