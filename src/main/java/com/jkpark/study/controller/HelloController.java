package com.jkpark.study.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
	@GetMapping("/hello")
	public String getHello() {
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