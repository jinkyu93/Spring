package com.jkpark.study.security.context;

import com.jkpark.study.global.domain.Account;
import com.jkpark.study.global.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// service 의 dto 와 security 의 User 객체를 연결하는 class
public class UserContext extends User {
	private Account account;

	private UserContext(Account account, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.account = account;
	}

	public static UserContext fromAccountModel(Account account) {
		// Role 설정에 대한 분석 필요
		return new UserContext(
				account,
				account.getId(),
				account.getPw(),
				userRoleList(account.getRole())
		);
	}

	private static List<SimpleGrantedAuthority> userRoleList(Role role) {
		return Arrays
				.asList(role)
				.stream()
				.map(r -> new SimpleGrantedAuthority(r.name()))
				.collect(Collectors.toList()
				);
	}

	public final Account getAccount() {
		return account;
	}
}