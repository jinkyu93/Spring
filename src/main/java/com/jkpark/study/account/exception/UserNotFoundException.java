package com.jkpark.study.account.exception;

public class UserNotFoundException extends RuntimeException {
	private static final String MESSAGE = "유저를 찾지 못했습니다.";

	public UserNotFoundException() {
		super(MESSAGE);
	}
}
