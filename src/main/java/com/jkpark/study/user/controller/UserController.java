package com.jkpark.study.user.controller;

import com.jkpark.study.global.dto.UserDTO;
import com.jkpark.study.global.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// @Controller has @Component. so it will be java bean because of @ComponentScan annotation
@RestController
@Slf4j
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping("/user")
	public UserDTO getHello(@RequestParam(value = "id", required = true) String id) {
		//UserDao user = service.search(id);

		//log.debug("getHello : {}", user.toString());

		//return user;

		return null;
	}

	@PostMapping("/user")
	public String postHello(@RequestBody UserDTO user) {
		service.upsert(user);

		log.debug("postHello : {}", user.toString());

		return "hello";
	}

	@PutMapping("/user")
	public String putHello() {

		return "hello";
	}

	@DeleteMapping("/user")
	public String deleteHello(@RequestParam(value = "id", required = true) String id) {
		//service.remove(id);

		//log.debug("deleteHello : {}", id);

		return "hello";
	}
}