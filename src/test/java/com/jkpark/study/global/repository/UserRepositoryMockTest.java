package com.jkpark.study.global.repository;

import com.jkpark.study.global.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
//@Transactional
public class UserRepositoryMockTest {
	// mock 이 아닌 실제 db 와 테스트 하는 로직 작성 필요
	@MockBean
	private UserRepository dao;

	private final String testIdValue = "admin";
	private final String testPasswordValue = "pass";

	@Test
	public void findByIdSuccess() {
		User mockData = makeTestUser();

		when(dao.findById(testIdValue))
				.thenReturn(Optional.of(mockData));

		User result = dao.findById(testIdValue).orElse(null);

		assertEquals(result.getId(), mockData.getId());
		assertEquals(result.getPw(), mockData.getPw());
	}

	@Test
	public void findByIdNothing() {
		when(dao.findById(testIdValue))
				.thenReturn(Optional.empty());

		User result = dao.findById(testIdValue).orElse(null);

		assertNull(result);
	}

	@Test
	public void saveSuccess() {
		User mockData = makeTestUser();

		when(dao.save(mockData))
				.thenReturn(mockData);

		User result = dao.save(mockData);

		assertEquals(result.getUuid(), mockData.getUuid());
		assertEquals(result.getId(), mockData.getId());
		assertEquals(result.getPw(), mockData.getPw());
	}

	@Test
	public void saveDuplicate() {
		User mockData = makeTestUser();

		when(dao.save(mockData))
				.thenThrow(IllegalArgumentException.class);

		assertThrows(IllegalArgumentException.class, () -> dao.save(mockData));
	}

	private User makeTestUser() {
		return new User(testIdValue, testPasswordValue);
	}
}