package com.jkpark.study.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class HelloController {
	@GetMapping("/hello")
	public String getHello() {
		log.debug("lombok : {}", "helloooooooooooooooooooooooooooooooooooooooo");
		return "hello";
	}

	@PostMapping("/hello")
	public String postHello() {
		return "hello";
	}

	@PutMapping("/hello")
	public String putHello() {
		return "hello";
	}

	@DeleteMapping("/hello")
	public String deleteHello() {
		return "hello";
	}
}