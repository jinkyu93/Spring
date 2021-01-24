package com.jkpark.study.data.service;

import com.jkpark.study.global.domain.Account;
import com.jkpark.study.global.domain.Data;
import com.jkpark.study.global.domain.Role;
import com.jkpark.study.global.dto.AccountDTO;
import com.jkpark.study.global.dto.DataDTO;
import com.jkpark.study.global.repository.DataRepository;
import com.jkpark.study.global.service.DataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
@AllArgsConstructor
public class DataServiceImpl implements DataService {
	private DataRepository dao;

	@Override
	public DataDTO insert(DataDTO data) {
		Data createdData = dao.save(data.toEntity());
		return dataToDataDTO(createdData);
	}

	@Override
	public Set<DataDTO> findById(String id) {
		return null;
	}

	@Override
	public Set<DataDTO> findAll() {
		return null;
	}

	private DataDTO dataToDataDTO(Data data) {
		Date date = data.getCreated_time();
		int val = data.getVal();

		DataDTO dto = new DataDTO(date, val);

		return dto;
	}
}
