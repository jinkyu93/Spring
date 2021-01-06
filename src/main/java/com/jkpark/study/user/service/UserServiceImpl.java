package com.jkpark.study.user.service;

import com.jkpark.study.global.domain.User;
import com.jkpark.study.global.dto.UserDTO;
import com.jkpark.study.global.repository.UserRepository;
import com.jkpark.study.global.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository dao;

	@Override
	public UserDTO insert(UserDTO user) {
		// need to encrypt the password
		User selectedUser = dao.findById(user.getId()).orElse(null);

		// 이런 null 체크 로직을 줄일 수 있는 방법이 없을까
		if(selectedUser != null) {
			return null;
		}

		User createdUser = dao.save(user.toEntity());

		return userToUserDTO(createdUser);
	}

	@Override
	public UserDTO findById(String id) {
		Optional<User> resultSet = dao.findById(id);

		// null check 를 Optional 을 통해서 함수형으로 처리할 수 있도록 연구 필요
		// orElse(null) 이런 식으로
		if(resultSet.isPresent() == false) {
			return null;
		}

		User selectedUser = resultSet.get();

		return userToUserDTO(selectedUser);
	}

	private UserDTO userToUserDTO(User selectedUser) {
		if(selectedUser == null) {
			return null;
		}

		UserDTO dto = new UserDTO();
		dto.setId(selectedUser.getId());
		dto.setPw(selectedUser.getPw());
		return dto;
	}
}
