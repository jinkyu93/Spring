package com.jkpark.study.data.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DataControllerMockTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser
	public void getSampleDataFound() throws Exception {
		RequestBuilder builder = get(DataController.DEFAULT_PATH + "/sample");

		mockMvc.perform(builder)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}
}
