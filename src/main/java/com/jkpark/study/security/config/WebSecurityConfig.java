package com.jkpark.study.security.config;

import com.jkpark.study.security.filter.AuthenticationFilter;
import com.jkpark.study.security.handler.AuthenticationFailureHandler;
import com.jkpark.study.security.handler.AuthenticationSuccessHandler;
import com.jkpark.study.security.provider.AuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// WebSecurityConfigurerAdapter 를 상속받은 Class 에
// @EnableWebSecurity annotation 을 추가하면 SpringSecurityFilterChain 에 포함된다.
// SpringSecurityFilterChain 은 DelegatingFilterProxy 에서 동작하는데 이놈은 DispatcherServlet 보다 먼저 만나는 놈이다.
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final String loginUrl = "/account/login";
	private final String refreshUrl = "/account/refresh";

	private AuthenticationSuccessHandler loginAuthenticationSuccessHandler;
	private AuthenticationFailureHandler loginAuthenticationFailureHandler;
	private AuthenticationSuccessHandler refreshAuthenticationSuccessHandler;
	private AuthenticationFailureHandler refreshAuthenticationFailureHandler;

	private AuthenticationProvider provider;

	// TODO : 원리 이해하고 더 좋은 방법 찾아보기
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	protected AuthenticationFilter getLoginFilter() throws Exception {
		AuthenticationFilter filter = new AuthenticationFilter(loginUrl, loginAuthenticationSuccessHandler, loginAuthenticationFailureHandler);
		filter.setAuthenticationManager(this.getAuthenticationManager());

		return filter;
	}

	protected AuthenticationFilter getRefreshFilter() throws Exception {
		AuthenticationFilter filter = new AuthenticationFilter(refreshUrl, refreshAuthenticationSuccessHandler, refreshAuthenticationFailureHandler);
		filter.setAuthenticationManager(this.getAuthenticationManager());

		return filter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(this.provider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			.disable();

		http.headers()
			.frameOptions()
			.disable();

		http.addFilterBefore(getLoginFilter(), UsernamePasswordAuthenticationFilter.class);
		// TODO : Refresh Filter 만들기
		// 나중에 하자...
		// refresh token 은 유효기간이 길기 때문에
		// 보안관련해서 신경쓸게 많다
		// ex) client side : http only cookie
		// ex) server side : refresh token 을 db 에 저장
		// source : https://medium.com/@d971106b/%EC%82%BD%EC%A7%88%EA%B8%B0%EB%A1%9D-%EB%A1%9C%EA%B7%B8%EC%9D%B8-api-%EC%9E%91%EC%84%B1-jwt-refresh-token-access-token-http-only-92570160fa1c
		//http.addFilterBefore(getRefreshFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}