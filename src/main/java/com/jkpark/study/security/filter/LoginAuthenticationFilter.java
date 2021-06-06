package com.jkpark.study.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkpark.study.global.dto.AccountDTO;
import com.jkpark.study.security.handler.LoginAuthenticationFailureHandler;
import com.jkpark.study.security.handler.LoginAuthenticationSuccessHandler;
import com.jkpark.study.security.token.PreAuthorizationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
	private LoginAuthenticationFailureHandler loginAuthenticationFailureHandler;

	protected LoginAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	// TODO : 더 가독성 좋은 줄바꿈에 대해서 고민해보기
	public LoginAuthenticationFilter(
			String defaultUrl,
			LoginAuthenticationSuccessHandler successHandler,
			LoginAuthenticationFailureHandler failureHandler) {
		super(defaultUrl);
		this.loginAuthenticationSuccessHandler = successHandler;
		this.loginAuthenticationFailureHandler = failureHandler;
	}

	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest req,
			HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
		AccountDTO dto = new ObjectMapper().readValue(req.getReader(), AccountDTO.class);
		PreAuthorizationToken token = new PreAuthorizationToken(dto);

		// PreAuthorizationToken 해당 객체에 맞는 Provider 를
		// getAuthenticationManager 해당 메서드가 자동으로 찾아서 연결해 준다.
		return super.getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(
			HttpServletRequest req,
			HttpServletResponse res,
			FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		this.loginAuthenticationSuccessHandler.onAuthenticationSuccess(req, res, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(
			HttpServletRequest req,
			HttpServletResponse res,
			AuthenticationException failed) throws IOException, ServletException {
		this.loginAuthenticationFailureHandler.onAuthenticationFailure(req, res, failed);
	}
}