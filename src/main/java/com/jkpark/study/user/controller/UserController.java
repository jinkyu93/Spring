package com.jkpark.study.user.controller;

import com.jkpark.study.global.impl.IUserService;
import com.jkpark.study.global.model.UserModel;
import com.jkpark.study.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// @Controller has @Component. so it will be java bean because of @ComponentScan annotation
@RestController
@Slf4j
public class UserController {

	@Autowired
	private IUserService service;

	@GetMapping("/user")
	public UserModel getHello(@RequestParam(value = "id", required = true) String id) {
		UserModel userModel = service.search(id);

		log.debug("getHello : {}", userModel.toString());

		return userModel;
	}

	@PostMapping("/user")
	public String postHello(@RequestBody UserModel userModel) {
		service.add(userModel);

		log.debug("postHello : {}", userModel.toString());

		return "hello";
	}

	@PutMapping("/user")
	public String putHello() {

		return "hello";
	}

	@DeleteMapping("/user")
	public String deleteHello(@RequestParam(value = "id", required = true) String id) {
		service.remove(id);

		log.debug("deleteHello : {}", id);

		return "hello";	}
}