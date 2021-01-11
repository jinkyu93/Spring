package com.jkpark.study.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Transient;

@Getter
@Setter
@NoArgsConstructor
public class TokenDTO {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String token;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String id;

	public TokenDTO(String token, String id) {
		super();
		this.token = token;
		this.id = id;
	}
}
