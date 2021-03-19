package com.jkpark.study.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkpark.study.account.exception.AccountConflictException;
import com.jkpark.study.account.exception.AccountNotFoundException;
import com.jkpark.study.global.domain.Role;
import com.jkpark.study.global.dto.AccountDTO;
import com.jkpark.study.global.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// webmvctest 는 spring context 를 전체 다 조회하고 bean 을 만들어 주지 않는다
// 때문에 Spring Security 의 Filter 가 등록될 때 있어야 할 bean 들이 만들어 지지 않는다.
// 해당 부분을 Scan 할 필요가 있다.
//@WebMvcTest(AccountController.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
public class AccountControllerMockTest {

	@Autowired
	private MockMvc mockMvc;

	// @WebMvcTest 는 웹과 관련된 Bean 만 주입된다
	// ex) @Controller
	// @Component 관련 Bean 은 주입되지 않기 때문에 Test 시에는 @MockBean 으로 가짜 객체를 주입시켜 줘야한다
	// @MockBean 내부는 텅 빈 Service 객체를 가져서 실제와 같이 동작을 하지 않는다
	// 전수테스트가 아닌 유닛테스트
	// Service 가 아닌 Controller 와의 데이터 통신만을 테스트 하기 위한 Unit Test Model
	@MockBean
	private AccountService service;

	private ObjectMapper mapper = new ObjectMapper();

	private final String urlTemplate = AccountController.ACCOUNT_PATH;

	// nameof 키워드와 같은게 없다...
	private final String testIdKey = "id";
	private final String testPasswordKey = "pw";

	private final String testIdValue = "admin";
	private final String testPasswordValue = "pass";
	private final Role testRoleValue = Role.ADMIN;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();;

	@Test
	public void getUserFound() throws Exception {
		AccountDTO mockData = makeTestUserDTO();

		// mock service 에서 반환할 mock data 설정
		when(service.findById(testIdValue))
				.thenReturn(mockData);

		RequestBuilder builder = get(urlTemplate).param(testIdKey, testIdValue);

		// TODO : 더 좋은방법 찾기
		// 상수를 사용하는게 아니라
		// model 의 변경에 테스트코드도 동적으로 변경될 수 있도록 수정할 수 없을까?
		mockMvc.perform(builder)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(mockData.getId()))
				.andExpect(jsonPath("$.pw").value(mockData.getPw()))
				.andExpect(jsonPath("$.role").value(mockData.getRole().toString()))
				.andDo(document("account/get/success"));
	}

	@Test
	public void getUserNotFound() throws Exception {
		when(service.findById(testIdValue))
				.thenThrow(new AccountNotFoundException());

		RequestBuilder builder = get(urlTemplate).param(testIdKey, testIdValue);
		mockMvc.perform(builder)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isNotFound())
				.andDo(document("account/get/failure"));
	}

	@Test
	public void postUserCreated() throws Exception {
		AccountDTO mockData = makeTestUserDTO();

		// mockData 의 eq 를 하면 왜 null 을 return 할까?
		when(service.insert(any(AccountDTO.class)))
				.thenReturn(mockData);

		String content = mapper.writeValueAsString(mockData);

		RequestBuilder builder = post(urlTemplate)
								.contentType(MediaType.APPLICATION_JSON)
								.content(content);
		mockMvc.perform(builder)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andDo(document("account/post/success"));
	}

	@Test
	public void postUserConflict() throws Exception {
		AccountDTO mockData = makeTestUserDTO();

		when(service.insert(any(AccountDTO.class)))
				.thenThrow(new AccountConflictException());

		String content = mapper.writeValueAsString(mockData);

		RequestBuilder builder = post(urlTemplate)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		mockMvc.perform(builder)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isConflict())
				.andDo(document("account/post/failure"));
	}

	private AccountDTO makeTestUserDTO() {
		AccountDTO mockData = new AccountDTO();
		mockData.setId(testIdValue);
		mockData.setPw(passwordEncoder.encode(testPasswordValue));
		mockData.setRole(testRoleValue);
		return mockData;
	}
}