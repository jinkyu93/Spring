package com.jkpark.study.account.exception;

public class AccountNotFoundException extends RuntimeException {
	private static final String MESSAGE = "계정을 찾지 못했습니다.";

	public AccountNotFoundException() {
		super(MESSAGE);
	}
}
