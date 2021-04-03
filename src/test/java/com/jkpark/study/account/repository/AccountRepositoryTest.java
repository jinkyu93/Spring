package com.jkpark.study.account.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.jkpark.study.common.dbunit.DbUnitConfig;
import com.jkpark.study.global.domain.Account;
import com.jkpark.study.global.domain.Role;
import com.jkpark.study.global.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Import(DbUnitConfig.class)
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
@DatabaseSetup(value = { // 각 test 실행 전
		"/dbunit/account.xml"
}, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = { // 각 test 실행 후
		"/dbunit/account.xml"
}, type = DatabaseOperation.DELETE_ALL)
@TestExecutionListeners(
		value = {
			DbUnitTestExecutionListener.class,
			DependencyInjectionTestExecutionListener.class // bean 을 di 받기 위해서 필요
		},
		// default 값인 replace 의 경우 spring-boot-test 에서 기본적으로 loading 해주는 TestExecutionListeners 들이 무시된다.
		// ex) @MockBean
		mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
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
		Account testData = makeTestAccount();
		Account result = dao.findById(testIdValue).get();

		assertEquals(result.getId(), testData.getId());
		assertEquals(result.getPw(), testData.getPw());
	}

	@Test
	public void findByIdNothing() {
		Account result = dao.findById(testNewIdValue).orElse(null);

		assertNull(result);
	}

	@Test
	public void saveSuccess() {
		Account testData = makeTestNewAccount();

		Account result = dao.save(testData);

		assertEquals(result.getUuid(), testData.getUuid());
		assertEquals(result.getId(), testData.getId());
		assertEquals(result.getPw(), testData.getPw());
	}

	@Test
	public void saveConflict() {
		Account testData = makeTestAccount();
		assertThrows(DataIntegrityViolationException.class, () -> dao.save(testData));
	}

	private Account makeTestAccount() {
		return new Account(testIdValue, testEncodedPasswordValue, testRoleValue);
	}

	private Account makeTestNewAccount() {
		return new Account(testNewIdValue, testNewEncodedPasswordValue, testNewRoleValue);
	}
}