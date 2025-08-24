package com.jkpark.study.account.service;

import com.jkpark.study.account.exception.AccountConflictException;
import com.jkpark.study.account.exception.AccountNotFoundException;
import com.jkpark.study.account.dao.Account;
import com.jkpark.study.account.enums.Role;
import com.jkpark.study.account.dto.AccountDTO;
import com.jkpark.study.account.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {
	private AccountRepository dao;

	private BCryptPasswordEncoder passwordEncoder;

	public AccountDTO insert(AccountDTO account) {
		checkAccountIsExist(account);
		encodePassword(account);
		Account createdAccount = dao.save(account.toEntity());

		return accountToAccountDTO(createdAccount);
	}

	private void encodePassword(AccountDTO account) {
		// encoding 을 insert 시에만 하면 되는가?
		String rawPw = account.getPw();
		String encodedPw = passwordEncoder.encode(rawPw);
		account.setPw(encodedPw);
	}

	private void checkAccountIsExist(AccountDTO account) {
		Account selectedAccount = dao.findById(account.getId()).orElse(null);

		// 이런 null 체크 로직을 줄일 수 있는 방법이 없을까
		if(selectedAccount != null) {
			throw new AccountConflictException();
		}
	}

	public AccountDTO findById(String id) {
		Optional<Account> optionalAccount = dao.findById(id);
		Account selectedAccount = optionalAccount.orElseThrow(AccountNotFoundException::new);
		return accountToAccountDTO(selectedAccount);
	}

	private AccountDTO accountToAccountDTO(Account selectedAccount) {
		String id = selectedAccount.getId();
		String pw = selectedAccount.getPw();
		Role role = selectedAccount.getRole();

		return new AccountDTO(id, pw, role);
	}
}
