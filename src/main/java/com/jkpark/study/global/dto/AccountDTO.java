package com.jkpark.study.global.dto;

import com.jkpark.study.global.domain.Account;
import com.jkpark.study.global.domain.Role;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
	private String id;

	private String pw;

	private Role role;

	public Account toEntity() {
		return new Account(id, pw, role);
	}
}
