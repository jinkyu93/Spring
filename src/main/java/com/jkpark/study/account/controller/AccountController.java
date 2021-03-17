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
	public static String ACCOUNT_PATH = "/account";

	private AccountService service;

	@GetMapping("/account")
	public ResponseEntity<AccountDTO> getAccount(@RequestParam(value = "id", required = true) String id) {
		AccountDTO selectedUser = service.findById(id);

		log.debug("getAccount : {}", selectedUser);
		return ResponseEntity.ok(selectedUser);
	}

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