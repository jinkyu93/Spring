package com.jkpark.study.global.dto;

import com.jkpark.study.global.domain.Account;
import com.jkpark.study.global.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AccountDTO {
	private String id;

	private String pw;

	private Role role;

	public Account toEntity() {
		return new Account(id, pw, role);
	}
}
