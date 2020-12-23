package com.jkpark.study.global.impl;

import com.jkpark.study.global.model.UserModel;

public interface IUserService {
	void add(UserModel userModel);

	void remove(String id);

	UserModel search(String id);
}
