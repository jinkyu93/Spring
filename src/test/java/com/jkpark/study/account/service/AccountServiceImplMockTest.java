package com.jkpark.study.account.service;

import com.jkpark.study.account.exception.AccountConflictException;
import com.jkpark.study.account.exception.AccountNotFoundException;
import com.jkpark.study.global.domain.Account;
import com.jkpark.study.global.domain.Role;
import com.jkpark.study.global.dto.AccountDTO;
import com.jkpark.study.global.repository.AccountRepository;
import com.jkpark.study.global.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceImplMockTest {
	@Autowired
	private AccountService service;

	@MockBean
	AccountRepository repository;

	@MockBean
	BCryptPasswordEncoder passwordEncoder;

	private final String testIdValue = "admin";
	private final String testPasswordValue = "pass";
	private final String testEncodedPasswordValue = "$2a$10$1kQc1e7xnJvxLVhy9tP6DO6a4hm1cyFdh/.MDGdnpkHJa9o3jywlW";
	private final Role testRoleValue = Role.ADMIN;

	@Test
	public void findByIdSuccess() {
		var mockData = Optional.of(new Account(testIdValue, testPasswordValue, testRoleValue));

		when(repository.findById(testIdValue))
				.thenReturn(mockData);

		AccountDTO result = service.findById(testIdValue);

		assertEquals(result.getId(), mockData.get().getId());
		assertEquals(result.getPw(), mockData.get().getPw());
		assertEquals(result.getRole(), mockData.get().getRole());
	}

	@Test
	public void findByIdNothing() {
		when(repository.findById(testIdValue))
				.thenReturn(Optional.empty());

		assertThrows(AccountNotFoundException.class, () -> service.findById(testIdValue));
	}

	@Test
	public void insertSuccess() {
		AccountDTO mockData = makeTestAccountDTO();
		AccountDTO encodedMockData = makeTestEncodedAccountDTO();

		// TODO : 한 줄씩 디버그하면 optional toString 오류가 난다. 확인할 것
		when(repository.findById(mockData.getId()))
				.thenReturn(Optional.empty());
		when(repository.save(any()))
				.thenReturn(encodedMockData.toEntity());
		when(passwordEncoder.encode(testPasswordValue))
				.thenReturn(testEncodedPasswordValue);

		AccountDTO result = service.insert(mockData);

		assertEquals(result.getId(), encodedMockData.getId());
		assertEquals(result.getPw(), encodedMockData.getPw());
		assertEquals(result.getRole(), encodedMockData.getRole());
	}

	@Test
	public void insertDuplicate() {
		AccountDTO encodedMockData = makeTestEncodedAccountDTO();

		when(repository.findById(encodedMockData.getId()))
				.thenReturn(Optional.of(encodedMockData.toEntity()));

		assertThrows(AccountConflictException.class, () -> service.insert(encodedMockData));
	}

	private AccountDTO makeTestAccountDTO() {
		AccountDTO mockData = new AccountDTO();
		mockData.setId(testIdValue);
		mockData.setPw(testPasswordValue);
		mockData.setRole(testRoleValue);

		return mockData;
	}

	private AccountDTO makeTestEncodedAccountDTO() {
		AccountDTO mockData = new AccountDTO();
		mockData.setId(testIdValue);
		mockData.setPw(testEncodedPasswordValue);
		mockData.setRole(testRoleValue);

		return mockData;
	}
}
