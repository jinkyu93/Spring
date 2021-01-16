package com.jkpark.study.global.service;

import com.jkpark.study.global.dto.AccountDTO;

public interface AccountService {
	AccountDTO insert(AccountDTO user);

	AccountDTO findById(String id);
}
