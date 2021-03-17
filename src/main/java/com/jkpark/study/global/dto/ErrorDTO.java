package com.jkpark.study.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
	// TODO : Error DTO 뿐 아니라 일반적으로 사용할 수 있는 Response DTO 를 만드는 것 고민할 것
	private Date timestamp;
	private int status;
	private String error;
	private String message;
	private String path;
}
