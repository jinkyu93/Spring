package com.jkpark.study.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkpark.study.account.exception.AccountConflictException;
import com.jkpark.study.account.exception.AccountNotFoundException;
import com.jkpark.study.account.service.AccountService;
import com.jkpark.study.account.enums.Role;
import com.jkpark.study.account.dto.AccountDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// webmvctest 는 spring context 를 전체 다 조회하고 bean 을 만들어 주지 않는다
// 때문에 Spring Security 의 Filter 가 등록될 때 있어야 할 bean 들이 만들어 지지 않는다.
// 해당 부분을 Scan 할 필요가 있다.
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerMockTest {

	@Autowired
	private MockMvc mockMvc;

	// @WebMvcTest 는 웹과 관련된 Bean 만 주입된다
	// ex) @Controller
	// @Component 관련 Bean 은 주입되지 않기 때문에 Test 시에는 @MockBean 으로 가짜 객체를 주입시켜 줘야한다
	// @MockBean 내부는 텅 빈 Service 객체를 가져서 실제와 같이 동작을 하지 않는다
	// 전수테스트가 아닌 유닛테스트
	// Service 가 아닌 Controller 와의 데이터 통신만을 테스트 하기 위한 Unit Test Model
	@MockitoBean
	private AccountService service;

	private ObjectMapper mapper = new ObjectMapper();

	private final String urlTemplate = AccountController.DEFAULT_PATH;

	// nameof 키워드와 같은게 없다...
	private final String testIdKey = "id";
	private final String testPasswordKey = "pw";

	private final String testIdValue = "admin";
	private final String testPasswordValue = "pass";
	private final Role testRoleValue = Role.ADMIN;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Test
	public void getUserFound() throws Exception {
		// given
		var mockData = makeTestUserDTO();
		var builder = get(urlTemplate).param(testIdKey, testIdValue);
		when(service.findById(testIdValue))
				.thenReturn(mockData);

		// when
		var resultActions = mockMvc.perform(builder);

		// then
		// TODO : 더 좋은방법 찾기
		// jsonPath 의 key 로 상수를 사용하는게 아니라
		// model 의 변경에 따라 테스트코드도 동적으로 변경될 수 있도록 수정할 수 없을까?
		resultActions.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(mockData.getId()))
				.andExpect(jsonPath("$.pw").value(mockData.getPw()))
				.andExpect(jsonPath("$.role").value(mockData.getRole().toString()));
	}

	@Test
	public void getUserNotFound() throws Exception {
		// given
		RequestBuilder builder = get(urlTemplate).param(testIdKey, testIdValue);
		when(service.findById(testIdValue))
				.thenThrow(new AccountNotFoundException());

		// when
		var resultActions = mockMvc.perform(builder);

		// then
		resultActions.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void postUserCreated() throws Exception {
		// given
		var mockData = makeTestUserDTO();
		var content = mapper.writeValueAsString(mockData);
		var builder = post(urlTemplate)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		when(service.insert(any(AccountDTO.class)))
				.thenReturn(mockData);
		// when
		var resultActions = mockMvc.perform(builder);

		// then
		resultActions.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}

	@Test
	public void postUserConflict() throws Exception {
		// given
		var mockData = makeTestUserDTO();
		var content = mapper.writeValueAsString(mockData);
		var builder = post(urlTemplate)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		when(service.insert(any(AccountDTO.class)))
				.thenThrow(new AccountConflictException());

		// when
		var resultActions = mockMvc.perform(builder);

		// then
		resultActions.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isConflict());
	}

	private AccountDTO makeTestUserDTO() {
		AccountDTO mockData = new AccountDTO();
		mockData.setId(testIdValue);
		mockData.setPw(passwordEncoder.encode(testPasswordValue));
		mockData.setRole(testRoleValue);
		return mockData;
	}
}