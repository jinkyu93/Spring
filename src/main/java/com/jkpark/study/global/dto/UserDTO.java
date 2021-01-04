package com.jkpark.study.global.dto;

import com.jkpark.study.global.domain.User;
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

	public User toEntity() {
		return new User(id, pw);
	}
}
