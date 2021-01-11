package com.jkpark.study.security.context;

import com.jkpark.study.global.domain.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserContext extends User {
	private com.jkpark.study.global.domain.User user;

	private UserContext(com.jkpark.study.global.domain.User user, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.user = user;
	}

	public static UserContext fromAccountModel(com.jkpark.study.global.domain.User user) {
		// Role 설정에 대한 분석 필요
		return new UserContext(
				user,
				user.getId(),
				user.getPw(),
				userRoleList(user.getRole())
		);
	}

	private static List<SimpleGrantedAuthority> userRoleList(UserRole role) {
		return Arrays
				.asList(role)
				.stream()
				.map(r -> new SimpleGrantedAuthority(r.name()))
				.collect(Collectors.toList()
				);
	}

	public final com.jkpark.study.global.domain.User getUser() {
		return user;
	}
}