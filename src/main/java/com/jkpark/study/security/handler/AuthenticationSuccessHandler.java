package com.jkpark.study.security.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkpark.study.security.dto.TokenDTO;
import com.jkpark.study.security.context.UserContext;
import com.jkpark.study.security.token.PostAuthorizationToken;
import com.jkpark.study.security.util.JwtFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
// 더 좋은 bean 을 만들기 위한 대체는 없나?
@Component
@AllArgsConstructor
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
	private JwtFactory factory;

	private ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest req,
			HttpServletResponse res,
			Authentication auth
	) throws IOException, ServletException {
		// Token 값을 정형화된 DTO를 만들어서 res 으로 내려주는 역활을 수행한다.
		// 인증결과 객체 auth 를 PostAuthorizationToken객체 변수에 담아줍니다.
		PostAuthorizationToken token = (PostAuthorizationToken) auth;
		UserContext context = (UserContext) token.getPrincipal();

		String authenticationToken = factory.generateAuthenticationToken(context);
		String refreshToken = factory.generateRefreshToken(context);

		String userId = token.getAccountContext().getAccountDTO().getId();

		processResponse(res, writeDto(authenticationToken, refreshToken, userId));
	}

	private TokenDTO writeDto(String authenticationToken, String refreshToken, String userId) {
		return new TokenDTO(authenticationToken, refreshToken, userId);
	}

	private void processResponse(HttpServletResponse res, TokenDTO dto) throws JsonProcessingException, IOException {
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		res.setStatus(HttpStatus.OK.value());
		res.getWriter().write(objectMapper.writeValueAsString(dto));
	}
}