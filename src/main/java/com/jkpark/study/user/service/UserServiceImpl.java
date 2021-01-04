package com.jkpark.study.user.service;

import com.jkpark.study.global.domain.User;
import com.jkpark.study.global.dto.UserDTO;
import com.jkpark.study.global.repository.UserRepository;
import com.jkpark.study.global.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository dao;

	@Override
	public User upsert(UserDTO dto) {
		// need to encrypt the password
		return dao.save(dto.toEntity());
	}
}
