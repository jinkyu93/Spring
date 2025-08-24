package com.jkpark.study.security.context;

import com.jkpark.study.account.enums.Role;
import com.jkpark.study.account.dto.AccountDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// service 의 dto 와 security 의 User 객체를 연결하는 class
public class UserContext extends User {
	private AccountDTO dto;

	private UserContext(AccountDTO dto, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.dto = dto;
	}

	public static UserContext fromAccountModel(AccountDTO dto) {
		return new UserContext(
				dto,
				dto.getId(),
				dto.getPw(),
				userRoleList(dto.getRole())
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

	public final AccountDTO getAccountDTO() {
		return dto;
	}
}