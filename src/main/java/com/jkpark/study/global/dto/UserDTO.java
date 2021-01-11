package com.jkpark.study.global.dto;

import com.jkpark.study.global.domain.User;
import com.jkpark.study.global.domain.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDTO {
	private String id;

	private String pw;

	private UserRole role;

	public User toEntity() {
		return new User(id, pw, role);
	}
}
