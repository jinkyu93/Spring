package com.jkpark.study.global.service;

import com.jkpark.study.global.domain.User;
import com.jkpark.study.global.dto.UserDTO;

public interface UserService {
	// 왜 return type 이 domain 이지?
	User upsert(UserDTO user);
}
