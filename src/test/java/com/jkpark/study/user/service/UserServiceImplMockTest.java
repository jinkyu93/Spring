package com.jkpark.study.user.service;

import com.jkpark.study.global.domain.UserRole;
import com.jkpark.study.global.dto.UserDTO;
import com.jkpark.study.global.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplMockTest {
	@MockBean
	private UserService service;

	private final String testIdValue = "admin";
	private final String testPasswordValue = "pass";
	private final UserRole testRoleValue = UserRole.ADMIN;

	@Test
	public void findByIdSuccess() {
		UserDTO mockData = makeTestUserDTO();

		when(service.findById(testIdValue))
				.thenReturn(mockData);

		UserDTO result = service.findById(testIdValue);

		assertEquals(result.getId(), mockData.getId());
		assertEquals(result.getPw(), mockData.getPw());
		assertEquals(result.getRole(), mockData.getRole());
	}

	@Test
	public void findByIdNothing() {
		when(service.findById(testIdValue))
				.thenReturn(null);

		UserDTO result = service.findById(testIdValue);

		assertNull(result);
	}

	@Test
	public void insertSuccess() {
		UserDTO mockData = makeTestUserDTO();

		when(service.insert(mockData))
				.thenReturn(mockData);

		UserDTO result = service.insert(mockData);

		assertEquals(result.getId(), mockData.getId());
		assertEquals(result.getPw(), mockData.getPw());
		assertEquals(result.getRole(), mockData.getRole());
	}


	@Test
	public void insertDuplicate() {
		UserDTO mockData = makeTestUserDTO();

		when(service.insert(mockData))
				.thenReturn(null);

		UserDTO result = service.insert(mockData);

		assertNull(result);
	}

	private UserDTO makeTestUserDTO() {
		UserDTO mockData = new UserDTO();
		mockData.setId(testIdValue);
		mockData.setPw(testPasswordValue);
		mockData.setRole(testRoleValue);

		return mockData;
	}
}
