package com.jkpark.study.account.exception;

import com.jkpark.study.account.controller.AccountController;
import com.jkpark.study.global.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

// 일반적으로 @ExceptionHandler 는 @Controller 로 선언된 클래스 내부에서만 적용된다.
// @RestControllerAdvice 는 @RestController 를 사용하는 클래스 전역으로 적용된다.
@RestControllerAdvice(assignableTypes = {AccountController.class})
public class UserExceptionHandler {
	// 특정 Exception 이 발생했을 때 이 메소드를 통해서 Response 를 생성한다.
	@ExceptionHandler({UserNotFoundException.class})
	public ResponseEntity<ErrorDTO> responseBadRequest(UserNotFoundException badRequestException) {
		// TODO : path 는 꼭 환경변수로 뺄 것
		var errorDto = new ErrorDTO(new Date(), HttpStatus.NOT_FOUND.value(), "UserNotFoundException", badRequestException.getMessage(), "/account");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
	}
}
