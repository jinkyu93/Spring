package com.jkpark.study.account.controller;

import com.jkpark.study.global.dto.AccountDTO;
import com.jkpark.study.global.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @Controller has @Component. so it will be java bean because of @ComponentScan annotation
@Slf4j
@RestController
@AllArgsConstructor
public class AccountController {
	private AccountService service;

	@GetMapping("/account")
	public ResponseEntity<AccountDTO> getAccount(@RequestParam(value = "id", required = true) String id) {
		AccountDTO selectedUser = service.findById(id);

		log.debug("getAccount : {}", selectedUser);
		ResponseEntity<AccountDTO> responseEntity = selectedUser == null ?
				new ResponseEntity(null, HttpStatus.NOT_FOUND) :
				new ResponseEntity(selectedUser, HttpStatus.FOUND);

		return responseEntity;
	}

	// response DTO 와 내부 DTO 를 분리하는게 좋을 것 같다.
	// 내부 DTO 는 모든 property 를 다 가지고
	// response DTO 는 사용할 property 만을 포함하도록 하게
	@PostMapping("/account")
	public ResponseEntity<AccountDTO> postAccount(@RequestBody AccountDTO user) {
		AccountDTO createdUser = service.insert(user);

		log.debug("postAccount : {}", user);

		ResponseEntity<AccountDTO> responseEntity = createdUser == null ?
				new ResponseEntity(null, HttpStatus.CONFLICT) :
				new ResponseEntity(createdUser, HttpStatus.CREATED);

		return responseEntity;
	}

	@PutMapping("/account")
	public String putAccount() {

		return "Account";
	}

	@DeleteMapping("/account")
	public String deleteAccount(@RequestParam(value = "id", required = true) String id) {
		//service.remove(id);

		//log.debug("deleteAccount : {}", id);

		return "Account";
	}
}