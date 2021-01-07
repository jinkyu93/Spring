package com.jkpark.study.user.controller;

import com.jkpark.study.global.dto.UserDTO;
import com.jkpark.study.global.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @Controller has @Component. so it will be java bean because of @ComponentScan annotation
@Slf4j
@RestController
@AllArgsConstructor
public class UserController {

	//@Autowired
	private UserService service;

	@GetMapping("/user")
	public ResponseEntity<UserDTO> getUser(@RequestParam(value = "id", required = true) String id) {
		UserDTO selectedUser = service.findById(id);

		log.debug("{}", selectedUser);
		ResponseEntity<UserDTO> responseEntity = selectedUser == null ?
				new ResponseEntity(null, HttpStatus.NOT_FOUND) :
				new ResponseEntity(selectedUser, HttpStatus.FOUND);

		return responseEntity;
	}

	// response DTO 와 내부 DTO 를 분리하는게 좋을 것 같다.
	// 내부 DTO 는 모든 property 를 다 가지고
	// response DTO 는 사용할 property 만을 포함하도록 하게
	@PostMapping("/user")
	public ResponseEntity<UserDTO> postUser(@RequestBody UserDTO user) {
		UserDTO createdUser = service.insert(user);

		log.debug("postUser : {}", user.toString());

		ResponseEntity<UserDTO> responseEntity = createdUser == null ?
				new ResponseEntity(null, HttpStatus.CONFLICT) :
				new ResponseEntity(createdUser, HttpStatus.CREATED);

		return responseEntity;
	}

	@PutMapping("/user")
	public String putUser() {

		return "User";
	}

	@DeleteMapping("/user")
	public String deleteUser(@RequestParam(value = "id", required = true) String id) {
		//service.remove(id);

		//log.debug("deleteUser : {}", id);

		return "User";
	}
}