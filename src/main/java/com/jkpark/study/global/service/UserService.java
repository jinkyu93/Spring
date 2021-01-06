package com.jkpark.study.global.service;

import com.jkpark.study.global.dto.UserDTO;

public interface UserService {
	UserDTO insert(UserDTO user);

	UserDTO findById(String id);
}
