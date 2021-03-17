package com.jkpark.study.account.service;

import com.jkpark.study.account.exception.UserNotFoundException;
import com.jkpark.study.global.domain.Account;
import com.jkpark.study.global.domain.Role;
import com.jkpark.study.global.dto.AccountDTO;
import com.jkpark.study.global.repository.AccountRepository;
import com.jkpark.study.global.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
	private AccountRepository dao;

	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public AccountDTO insert(AccountDTO account) {
		// need to encrypt the password
		Account selectedAccount = dao.findById(account.getId()).orElse(null);

		// 이런 null 체크 로직을 줄일 수 있는 방법이 없을까
		if(selectedAccount != null) {
			return new AccountDTO("", "", Role.USER);
		}

		// encoding 을 insert 시에만 하면 되는가?
		String rawPw = account.getPw();
		String encodedPw = passwordEncoder.encode(rawPw);
		account.setPw(encodedPw);

		Account createdAccount = dao.save(account.toEntity());

		return userToUserDTO(createdAccount);
	}

	@Override
	public AccountDTO findById(String id) {
		Optional<Account> optionalAccount = dao.findById(id);
		Account selectedAccount = optionalAccount.orElseThrow(UserNotFoundException::new);
		return userToUserDTO(selectedAccount);
	}

	private AccountDTO userToUserDTO(Account selectedAccount) {
		String id = Optional.of(selectedAccount.getId()).orElse("");
		String pw = Optional.of(selectedAccount.getPw()).orElse("");
		Role role = Optional.of(selectedAccount.getRole()).orElse(Role.USER);

		AccountDTO dto = new AccountDTO(id, pw, role);

		return dto;
	}
}
