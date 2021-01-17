package com.jkpark.study.security.service;

import com.jkpark.study.global.dto.AccountDTO;
import com.jkpark.study.global.service.AccountService;
import com.jkpark.study.security.context.UserContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private AccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AccountDTO dto = accountService.findById(username);

		if(dto == null) {
			dto = new AccountDTO();
		}
		return UserContext.fromAccountModel(dto);
	}
}
