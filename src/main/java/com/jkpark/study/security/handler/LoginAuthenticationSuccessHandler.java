package com.jkpark.study.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkpark.study.security.dto.TokenDTO;
import com.jkpark.study.security.context.UserContext;
import com.jkpark.study.security.token.PostAuthenticationToken;
import com.jkpark.study.security.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
// 더 좋은 bean 을 만들기 위한 대체는 없나?
@Component
@AllArgsConstructor
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private JwtUtil jwtUtil;

	private ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest req,
			HttpServletResponse res,
			Authentication auth
	) throws IOException, ServletException {
		// Token 값을 정형화된 DTO를 만들어서 res 으로 내려주는 역활을 수행한다.
		// 인증결과 객체 auth 를 PostAuthorizationToken객체 변수에 담아줍니다.
		PostAuthenticationToken token = (PostAuthenticationToken) auth;
		UserContext context = (UserContext) token.getPrincipal();

		String accessToken = jwtUtil.generateAccessToken(context);
		String refreshToken = jwtUtil.generateRefreshToken(context);

		String userId = token.getAccountContext().getAccountDTO().getId();

		processResponse(res, writeDto(accessToken, refreshToken, userId));
	}

	private TokenDTO writeDto(String accessToken, String refreshToken, String userId) {
		return new TokenDTO(accessToken, refreshToken, userId);
	}

	private void processResponse(HttpServletResponse res, TokenDTO dto) throws IOException {
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		res.setStatus(HttpStatus.OK.value());
		res.getWriter().write(objectMapper.writeValueAsString(dto));
	}
}