package com.jkpark.study.global.repository;

import com.jkpark.study.global.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByUuid(String uuid);
}
