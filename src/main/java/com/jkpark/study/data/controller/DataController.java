package com.jkpark.study.data.controller;

import com.jkpark.study.global.dto.AccountDTO;
import com.jkpark.study.global.dto.DataDTO;
import com.jkpark.study.global.service.DataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Slf4j
@RestController
@AllArgsConstructor
public class DataController {
	private DataService service;

	@GetMapping("/sample")
	public ResponseEntity<DataDTO> getSample() {
		DataDTO data = new DataDTO(new Date(), 10);

		log.debug("getSample : {}", data);
		ResponseEntity<DataDTO> responseEntity = new ResponseEntity(data, HttpStatus.OK);

		return responseEntity;
	}

	@GetMapping("/data")
	public ResponseEntity<Set<DataDTO>> getData() {
		Set<DataDTO> data = service.findAll();

		log.debug("getData : {}", data);
		ResponseEntity<Set<DataDTO>> responseEntity = data == null ?
				new ResponseEntity(null, HttpStatus.NOT_FOUND) :
				new ResponseEntity(data, HttpStatus.OK);

		return responseEntity;
	}

	@PostMapping("/data")
	public ResponseEntity<AccountDTO> postData(@RequestBody DataDTO data) {
		DataDTO createdData = service.insert(data);

		log.debug("postData : {}", data);

		ResponseEntity<AccountDTO> responseEntity = createdData == null ?
				new ResponseEntity(null, HttpStatus.CONFLICT) :
				new ResponseEntity(createdData, HttpStatus.CREATED);

		return responseEntity;
	}
}
