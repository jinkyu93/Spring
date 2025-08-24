package com.jkpark.study.account.repository;

import com.jkpark.study.account.dao.Account;
import com.jkpark.study.account.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

// Use @DataJpaTest for testing the persistence layer.
// It will set up an in-memory H2 database and configure Spring Data JPA.
@DataJpaTest
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

    private final Account testAccount = new Account(
            "admin",
            "$2a$10$1kQc1e7xnJvxLVhy9tP6DO6a4hm1cyFdh/.MDGdnpkHJa9o3jywlW",
            Role.ADMIN
    );

	@BeforeEach
	public void setUp() {
		accountRepository.save(testAccount);
	}

	@Test
	public void findByIdSuccess() {
		// when
		var result = accountRepository.findById(testAccount.getId()).orElse(null);

		// then
		assertNotNull(result);
		assertEquals(testAccount.getId(), result.getId());
		assertEquals(testAccount.getPw(), result.getPw());
		assertEquals(testAccount.getRole(), result.getRole());
	}

	@Test
	public void findByIdNothing() {
		// given
		var nonExistentId = "nonExistentId";

		// when
		var result = accountRepository.findById(nonExistentId).orElse(null);

		// then
		assertNull(result);
	}

	@Test
	public void saveSuccess() {
		// given
		var newAccount = new Account("newUser", "newPassword", Role.USER);

		// when
		var result = accountRepository.save(newAccount);
        var a = accountRepository.findById(newAccount.getId());

		// then
		assertNotNull(result.getUuid());
		assertEquals(newAccount.getId(), result.getId());
		assertEquals(newAccount.getPw(), result.getPw());
		assertEquals(newAccount.getRole(), result.getRole());
	}
}
