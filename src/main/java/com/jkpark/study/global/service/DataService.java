package com.jkpark.study.global.service;

import com.jkpark.study.global.dto.DataDTO;

import java.util.Set;

public interface DataService {
	DataDTO insert(DataDTO data);

	Set<DataDTO> findById(String id);

	Set<DataDTO> findAll();
}
