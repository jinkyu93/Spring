package com.jkpark.study.account.repository;

import com.jkpark.study.global.domain.Account;
import com.jkpark.study.global.domain.Role;
import com.jkpark.study.global.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountRepositoryTest {
	@Autowired
	private AccountRepository dao;

	// TODO : 더 좋은 관리방법 찾기?
	private final String testIdValue = "admin";
	private final String testEncodedPasswordValue = "$2a$10$1kQc1e7xnJvxLVhy9tP6DO6a4hm1cyFdh/.MDGdnpkHJa9o3jywlW";
	private final Role testRoleValue = Role.ADMIN;

	private final String testNewIdValue = "newAdmin";
	private final String testNewEncodedPasswordValue = "$33333333333333333333333333333333333333333333333333333333333";
	private final Role testNewRoleValue = Role.ADMIN;

	@Test
	public void findByIdSuccess() {
		// given
		var testData = makeTestAccount();

		// when
		var result = dao.findById(testIdValue).get();

		// then
		assertEquals(result.getId(), testData.getId());
		assertEquals(result.getPw(), testData.getPw());
	}

	@Test
	public void findByIdNothing() {
		// given + when
		var result = dao.findById(testNewIdValue).orElse(null);

		// then
		assertNull(result);
	}

	@Test
	public void saveSuccess() {
		// given
		var testData = makeTestNewAccount();

		// when
		var result = dao.save(testData);

		// then
		assertEquals(result.getUuid(), testData.getUuid());
		assertEquals(result.getId(), testData.getId());
		assertEquals(result.getPw(), testData.getPw());
	}

	@Test
	public void saveConflict() {
		// given
		var testData = makeTestAccount();

		// when
		var selectedAccount = dao.findById(testData.getId()).orElse(null);

		// then
		assertNotNull(selectedAccount);
	}

	private Account makeTestAccount() {
		return new Account(testIdValue, testEncodedPasswordValue, testRoleValue);
	}

	private Account makeTestNewAccount() {
		return new Account(testNewIdValue, testNewEncodedPasswordValue, testNewRoleValue);
	}
}