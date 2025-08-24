package com.jkpark.study.account.repository;

import com.jkpark.study.account.dao.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

// TODO : 기능별로 모듈 분리.
// global 이라는 package 에 interface 를 넣어놓은 이유는 각 기능들을 모듈로 분리할 수 있는 구조를 대비한 것
// domain 과 dto 는 global 에서 가지고 있는게 맞는지 추가 고민 필요
public interface AccountRepository extends JpaRepository<Account, UUID>{
	Optional<Account> findById(String id);
}
