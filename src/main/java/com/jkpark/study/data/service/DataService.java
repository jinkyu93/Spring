package com.jkpark.study.data.service;

import com.jkpark.study.data.dao.Data;
import com.jkpark.study.data.dto.DataDTO;
import com.jkpark.study.data.repository.DataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
@AllArgsConstructor
public class DataService {
	private DataRepository dao;

	public DataDTO insert(DataDTO data) {
		Data createdData = dao.save(data.toEntity());
		return dataToDataDTO(createdData);
	}

	public Set<DataDTO> findById(String id) {
		return null;
	}

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
