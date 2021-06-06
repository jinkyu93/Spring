package com.jkpark.study.security.config;

import com.jkpark.study.security.filter.JwtFilter;
import com.jkpark.study.security.filter.LoginAuthenticationFilter;
import com.jkpark.study.security.handler.LoginAuthenticationFailureHandler;
import com.jkpark.study.security.handler.LoginAuthenticationSuccessHandler;
import com.jkpark.study.security.provider.JwtProvider;
import com.jkpark.study.security.provider.LoginAuthenticationProvider;
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

	private LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
	private LoginAuthenticationFailureHandler loginAuthenticationFailureHandler;
	// TODO : Refresh 전용 새로운 객체 만들 것
	private LoginAuthenticationSuccessHandler refreshLoginAuthenticationSuccessHandler;
	private LoginAuthenticationFailureHandler refreshLoginAuthenticationFailureHandler;

	private LoginAuthenticationProvider loginAuthenticationProvider;
	private JwtProvider jwtProvider;


	// TODO : 원리 이해하고 더 좋은 방법 찾아보기
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	// TODO : bean 으로 대체가 안되나?
	// setAuthenticationManager 때문에 안되나?
	protected LoginAuthenticationFilter getLoginFilter() throws Exception {
		LoginAuthenticationFilter filter = new LoginAuthenticationFilter(loginUrl, loginAuthenticationSuccessHandler, loginAuthenticationFailureHandler);
		filter.setAuthenticationManager(this.getAuthenticationManager());

		return filter;
	}

	protected LoginAuthenticationFilter getRefreshFilter() throws Exception {
		LoginAuthenticationFilter filter = new LoginAuthenticationFilter(refreshUrl, refreshLoginAuthenticationSuccessHandler, refreshLoginAuthenticationFailureHandler);
		filter.setAuthenticationManager(this.getAuthenticationManager());

		return filter;
	}

	protected JwtFilter getJwtFilter() throws Exception {
		JwtFilter filter = new JwtFilter(this.jwtProvider);
		return filter;
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(this.loginAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO : 기능 확인 후 보안 적용할 것

		/**
		 * CookieCsrfTokenRepository.withHttpOnlyFalse())
		 * X-XSRF-TOKEN
		 * XSRF-TOKEN
		 */
		// cross-site request forgery
		http.csrf()
			.disable();

		// X-Frame-Options 헤더 설정
		http.headers()
			.frameOptions()
			.disable();

		http.authorizeRequests()
			.antMatchers("/account/**").permitAll() // 회원가입 등
			.antMatchers("/docs/**").permitAll() // rest docs
			.antMatchers("/h2-console/**").permitAll() // /h2-console 용
			.antMatchers("/**").authenticated();

		http.addFilterBefore(this.getJwtFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(this.getLoginFilter(), JwtFilter.class);

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