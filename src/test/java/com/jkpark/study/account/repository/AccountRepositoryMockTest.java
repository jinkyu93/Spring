package com.jkpark.study.account.repository;

import com.jkpark.study.global.domain.Account;
import com.jkpark.study.global.domain.Role;
import com.jkpark.study.global.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
//@Transactional
public class AccountRepositoryMockTest {
	// mock 이 아닌 실제 db 와 테스트 하는 로직 작성 필요
	@MockBean
	private AccountRepository dao;

	private BCryptPasswordEncoder passwordEncoder;

	private final String testIdValue = "admin";
	private final String testPasswordValue = "pass";
	private final Role testRoleValue = Role.ADMIN;

	@Test
	public void findByIdSuccess() {
		Account mockData = makeTestAccount();

		when(dao.findById(testIdValue))
				.thenReturn(Optional.of(mockData));

		Account result = dao.findById(testIdValue).orElse(null);

		assertEquals(result.getId(), mockData.getId());
		assertEquals(result.getPw(), mockData.getPw());
	}

	@Test
	public void findByIdNothing() {
		when(dao.findById(testIdValue))
				.thenReturn(Optional.empty());

		Account result = dao.findById(testIdValue).orElse(null);

		assertNull(result);
	}

	@Test
	public void saveSuccess() {
		Account mockData = makeTestAccount();

		when(dao.save(mockData))
				.thenReturn(mockData);

		Account result = dao.save(mockData);

		assertEquals(result.getUuid(), mockData.getUuid());
		assertEquals(result.getId(), mockData.getId());
		assertEquals(result.getPw(), mockData.getPw());
	}

	@Test
	public void saveDuplicate() {
		Account mockData = makeTestAccount();

		when(dao.save(mockData))
				.thenThrow(IllegalArgumentException.class);

		assertThrows(IllegalArgumentException.class, () -> dao.save(mockData));
	}

	private Account makeTestAccount() {
		return new Account(testIdValue, passwordEncoder.encode(testPasswordValue), testRoleValue);
	}
}