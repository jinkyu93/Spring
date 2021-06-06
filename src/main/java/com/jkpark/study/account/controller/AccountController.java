package com.jkpark.study.account.controller;

import com.jkpark.study.global.dto.AccountDTO;
import com.jkpark.study.global.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @Controller has @Component. so it will be java bean because of @ComponentScan annotation
@Slf4j
@RestController
@AllArgsConstructor
public class AccountController {
	// TODO : const 하게 저장하고 있는게 아니라 동작하는 도중에 동적으로 값을 전달해 줄 수는 없을까?
	// ex)HttpServletRequest
	public static final String DEFAULT_PATH = "/account";

	private AccountService service;

	@GetMapping("/account")
	public ResponseEntity<AccountDTO> getAccount(@RequestParam(value = "id", required = true) String id) {
		AccountDTO selectedUser = service.findById(id);
		log.debug("getAccount : {}", selectedUser);
		return new ResponseEntity(selectedUser, HttpStatus.OK);
	}

	@PostMapping("/account")
	public ResponseEntity<String> createAccount(@RequestBody AccountDTO account) {
		AccountDTO createdUser = service.insert(account);
		log.debug("createAccount : {}", createdUser);

		// TODO : String format 의 경우 속도가 느리다.
		// 반복문이 아니기 때문에 가독성을 우선으로 하느냐,
		// request 가 늘어날 경우를 대비해서 builder, joiner 등 성능을 우선으로 하느냐
		// 고민 필요
		// var body = StringUtils.arrayToDelimitedString(new Object[] {"account", createdUser.getId(),  "is created"}, " ");
		var body = String.format("account %1$s is created", createdUser.getId());
		return new ResponseEntity(body, HttpStatus.OK);
	}

	@PutMapping("/account")
	public String updateAccount() {

		return "Account";
	}

	@DeleteMapping("/account")
	public String deleteAccount(@RequestParam(value = "id", required = true) String id) {
		//service.remove(id);

		//log.debug("deleteAccount : {}", id);

		return "Account";
	}
}