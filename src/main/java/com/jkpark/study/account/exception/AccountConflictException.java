package com.jkpark.study.account.exception;

public class AccountConflictException extends RuntimeException {
	private static final String MESSAGE = "계정이 이미 존재합니다.";

	public AccountConflictException() {
		super(MESSAGE);
	}
}
