package com.jkpark.study.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkpark.study.global.dto.UserDTO;
import com.jkpark.study.global.repository.UserRepository;
import com.jkpark.study.global.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	// @WebMvcTest 는 웹과 관련된 Bean 만 주입된다
	// ex) @Controller
	// @Component 관련 Bean 은 주입되지 않기 때문에 Test 시에는 @MockBean 으로 가짜 객체를 주입시켜 줘야한다
	// @MockBean 내부는 텅 빈 Service 객체를 가져서 실제와 같이 동작을 하지 않는다
	// 전수테스트가 아닌 유닛테스트
	// Service 가 아닌 Controller 와의 데이터 통신만을 테스트 하기 위한 Unit Test Model
	@MockBean
	private UserService service;

	@MockBean
	private UserRepository dao;

	private ObjectMapper mapper = new ObjectMapper();

	private final String urlTemplate = "/user";

	// nameof 키워드와 같은게 없다...
	private final String testIdKey = "id";
	private final String testPasswordKey = "pw";

	private final String testIdValue = "admin";
	private final String testPasswordValue = "pass";

	@Test
	public void getUserFound() throws Exception {
		UserDTO mockData = makeTestUserDTO();

		// mock service 에서 반환할 mock data 설정
		when(service.findById(eq(testIdValue)))
				.thenReturn(mockData);

		RequestBuilder builder = get(urlTemplate).param(testIdKey, testIdValue);

		// jsonPath 에 하드코딩된 key 를 동적으로 바꿀 수 있는 방법 고민 필요
		// 안그러면 client 에 보내는 response dto 의 모습이 변할 때 마다 test 도 변경해야한다.
		// 점심 나가서 먹을 거 같아~~~
		mockMvc.perform(builder)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isFound())
				.andExpect(jsonPath("$.id").value(testIdValue))
				.andExpect(jsonPath("$.pw").value(testPasswordValue));
	}

	@Test
	public void getUserNotFound() throws Exception {
		when(service.findById(eq(testIdValue)))
				.thenReturn(null);

		RequestBuilder builder = get(urlTemplate).param(testIdKey, testIdValue);
		mockMvc.perform(builder)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void postUserCreated() throws Exception {
		UserDTO mockData = makeTestUserDTO();

		// mockData 의 eq 를 하면 왜 null 을 return 할까?
		when(service.insert(any(UserDTO.class)))
				.thenReturn(mockData);

		String content = mapper.writeValueAsString(mockData);

		RequestBuilder builder = post(urlTemplate)
								.contentType(MediaType.APPLICATION_JSON)
								.content(content);
		mockMvc.perform(builder)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isCreated());
	}

	@Test
	public void postUserConflict() throws Exception {
		UserDTO mockData = makeTestUserDTO();

		when(service.insert(any(UserDTO.class)))
				.thenReturn(null);

		String content = mapper.writeValueAsString(mockData);

		RequestBuilder builder = post(urlTemplate)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		mockMvc.perform(builder)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isConflict());
	}

	private UserDTO makeTestUserDTO() {
		UserDTO mockData = new UserDTO();
		mockData.setId(testIdValue);
		mockData.setPw(testPasswordValue);
		return mockData;
	}
}