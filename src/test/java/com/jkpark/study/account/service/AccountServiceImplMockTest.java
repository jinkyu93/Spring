package com.jkpark.study.account.service;

import com.jkpark.study.global.domain.Role;
import com.jkpark.study.global.dto.AccountDTO;
import com.jkpark.study.global.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceImplMockTest {
	@MockBean
	private AccountService service;

	private final String testIdValue = "admin";
	private final String testPasswordValue = "pass";
	private final Role testRoleValue = Role.ADMIN;

	@Test
	public void findByIdSuccess() {
		AccountDTO mockData = makeTestUserDTO();

		when(service.findById(testIdValue))
				.thenReturn(mockData);

		AccountDTO result = service.findById(testIdValue);

		assertEquals(result.getId(), mockData.getId());
		assertEquals(result.getPw(), mockData.getPw());
		assertEquals(result.getRole(), mockData.getRole());
	}

	@Test
	public void findByIdNothing() {
		when(service.findById(testIdValue))
				.thenReturn(null);

		AccountDTO result = service.findById(testIdValue);

		assertNull(result);
	}

	@Test
	public void insertSuccess() {
		AccountDTO mockData = makeTestUserDTO();

		when(service.insert(mockData))
				.thenReturn(mockData);

		AccountDTO result = service.insert(mockData);

		assertEquals(result.getId(), mockData.getId());
		assertEquals(result.getPw(), mockData.getPw());
		assertEquals(result.getRole(), mockData.getRole());
	}


	@Test
	public void insertDuplicate() {
		AccountDTO mockData = makeTestUserDTO();

		when(service.insert(mockData))
				.thenReturn(null);

		AccountDTO result = service.insert(mockData);

		assertNull(result);
	}

	private AccountDTO makeTestUserDTO() {
		AccountDTO mockData = new AccountDTO();
		mockData.setId(testIdValue);
		mockData.setPw(testPasswordValue);
		mockData.setRole(testRoleValue);

		return mockData;
	}
}
