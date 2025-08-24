package com.jkpark.study.account.service;

import com.jkpark.study.account.exception.AccountConflictException;
import com.jkpark.study.account.exception.AccountNotFoundException;
import com.jkpark.study.account.dao.Account;
import com.jkpark.study.account.enums.Role;
import com.jkpark.study.account.dto.AccountDTO;
import com.jkpark.study.account.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceMockTest {
	@Autowired
	private AccountService service;

	@MockitoBean
	AccountRepository repository;

	@MockitoBean
	BCryptPasswordEncoder passwordEncoder;

	private final String testIdValue = "admin";
	private final String testPasswordValue = "pass";
	private final String testEncodedPasswordValue = "$2a$10$1kQc1e7xnJvxLVhy9tP6DO6a4hm1cyFdh/.MDGdnpkHJa9o3jywlW";
	private final Role testRoleValue = Role.ADMIN;

	@Test
	public void findByIdSuccess() {
		// given
		var mockData = Optional.of(new Account(testIdValue, testPasswordValue, testRoleValue));
		when(repository.findById(testIdValue))
				.thenReturn(mockData);

		// when
		AccountDTO result = service.findById(testIdValue);

		// then
		assertEquals(result.getId(), mockData.get().getId());
		assertEquals(result.getPw(), mockData.get().getPw());
		assertEquals(result.getRole(), mockData.get().getRole());
	}

	@Test
	public void findByIdNothing() {
		// given
		when(repository.findById(testIdValue))
				.thenReturn(Optional.empty());

		// when + then
		assertThrows(AccountNotFoundException.class, () -> service.findById(testIdValue));
	}

	@Test
	public void insertSuccess() {
		// given
		var mockData = makeTestAccountDTO();
		var encodedMockData = makeTestEncodedAccountDTO();

		// TODO : 한 줄씩 디버그하면 optional toString 오류가 난다. 확인할 것
		when(repository.findById(mockData.getId()))
				.thenReturn(Optional.empty());
		when(repository.save(any()))
				.thenReturn(encodedMockData.toEntity());
		when(passwordEncoder.encode(testPasswordValue))
				.thenReturn(testEncodedPasswordValue);

		// when
		var result = service.insert(mockData);

		// then
		assertEquals(result.getId(), encodedMockData.getId());
		assertEquals(result.getPw(), encodedMockData.getPw());
		assertEquals(result.getRole(), encodedMockData.getRole());
	}

	@Test
	public void insertDuplicate() {
		// given
		var encodedMockData = makeTestEncodedAccountDTO();

		// when
		when(repository.findById(encodedMockData.getId()))
				.thenReturn(Optional.of(encodedMockData.toEntity()));

		// then
		assertThrows(AccountConflictException.class, () -> service.insert(encodedMockData));
	}

	private AccountDTO makeTestAccountDTO() {
		var mockData = new AccountDTO();
		mockData.setId(testIdValue);
		mockData.setPw(testPasswordValue);
		mockData.setRole(testRoleValue);

		return mockData;
	}

	private AccountDTO makeTestEncodedAccountDTO() {
		var mockData = new AccountDTO();
		mockData.setId(testIdValue);
		mockData.setPw(testEncodedPasswordValue);
		mockData.setRole(testRoleValue);

		return mockData;
	}
}
