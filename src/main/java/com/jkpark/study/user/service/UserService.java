package com.jkpark.study.user.service;

import com.jkpark.study.global.impl.IUserService;
import com.jkpark.study.global.model.UserModel;
import com.jkpark.study.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
	@Autowired
	UserDao dao;

	@Override
	public void add(UserModel userModel) {
		dao.add(userModel);
	}

	@Override
	public void remove(String id) {
		dao.remove(id);
	}

	@Override
	public UserModel search(String id) {
		return dao.search(id);
	}
}
