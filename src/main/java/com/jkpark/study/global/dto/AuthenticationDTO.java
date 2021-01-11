package com.jkpark.study.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Transient;

// TODO : 이거 DTO 비슷하게 중복되는거 같은데 하나로 합체 안되나
@Getter
@Setter
@NoArgsConstructor
public class AuthenticationDTO {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String id;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String pw;
}
