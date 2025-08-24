package com.jkpark.study.account.dto;

import com.jkpark.study.account.dao.Account;
import com.jkpark.study.account.enums.Role;
import lombok.*;

// 근데 이거 Setter 가 필요해?
// AllArgsConstructor 만 있으면 될 것 같은데
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
