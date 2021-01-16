package com.jkpark.study.user.service;

import com.jkpark.study.global.domain.Account;
import com.jkpark.study.global.dto.AccountDTO;
import com.jkpark.study.global.repository.AccountRepository;
import com.jkpark.study.global.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
	private AccountRepository dao;

	@Override
	public AccountDTO insert(AccountDTO account) {
		// need to encrypt the password
		Account selectedAccount = dao.findById(account.getId()).orElse(null);

		// 이런 null 체크 로직을 줄일 수 있는 방법이 없을까
		if(selectedAccount != null) {
			return null;
		}

		Account createdAccount = dao.save(account.toEntity());

		return userToUserDTO(createdAccount);
	}

	@Override
	public AccountDTO findById(String id) {
		Optional<Account> resultSet = dao.findById(id);

		// null check 를 Optional 을 통해서 함수형으로 처리할 수 있도록 연구 필요
		// orElse(null) 이런 식으로
		if(resultSet.isPresent() == false) {
			return null;
		}

		Account selectedAccount = resultSet.get();

		return userToUserDTO(selectedAccount);
	}

	private AccountDTO userToUserDTO(Account selectedAccount) {
		if(selectedAccount == null) {
			return null;
		}

		AccountDTO dto = new AccountDTO();
		dto.setId(selectedAccount.getId());
		dto.setPw(selectedAccount.getPw());
		dto.setRole(selectedAccount.getRole());

		return dto;
	}
}
