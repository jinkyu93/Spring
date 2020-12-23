package com.jkpark.study.user.dao;

import com.jkpark.study.global.impl.IUserDao;
import com.jkpark.study.global.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UserDao implements IUserDao {
	private HashMap<String, UserModel> db;

	public UserDao() {
		db = new HashMap<String, UserModel>();
	}

	@Override
	public void add(UserModel userModel) {
		db.put(userModel.getId(), userModel);
	}

	@Override
	public void remove(String id) {
		db.remove(id);
	}

	@Override
	public UserModel search(String id) {
		return db.get(id);
	}
}
