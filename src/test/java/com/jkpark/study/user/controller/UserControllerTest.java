package com.jkpark.study.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getUser() throws Exception {
		String content = "hello";

		mockMvc.perform(get("/user"))
				.andExpect(status().isOk())
				.andExpect(content().string(content));
	}

	@Test
	public void postUser() throws Exception {
		String content = "hello";

		mockMvc.perform(post("/user"))
				.andExpect(status().isOk())
				.andExpect(content().string(content));
	}

	@Test
	public void putUser() throws Exception {
		String content = "hello";

		mockMvc.perform(put("/user"))
				.andExpect(status().isOk())
				.andExpect(content().string(content));
	}

	@Test
	public void deleteUser() throws Exception {
		String content = "hello";

		mockMvc.perform(delete("/user"))
				.andExpect(status().isOk())
				.andExpect(content().string(content));
	}
}