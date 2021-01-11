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
// 아래 annotation 을 추가하면 SpringSecurityFilterChain 에 포함된다.
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private AuthenticationSuccessHandler loginAuthenticationSuccessHandler;
	private AuthenticationFailureHandler loginAuthenticationFailureHandler;

	private AuthenticationProvider provider;

	// TODO : 원리 이해하고 더 좋은 방법 찾아보기
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	protected AuthenticationFilter loginFilter() throws Exception {
		AuthenticationFilter filter = new AuthenticationFilter("/user/login", loginAuthenticationSuccessHandler, loginAuthenticationFailureHandler);
		filter.setAuthenticationManager(super.authenticationManagerBean());

		return filter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(this.provider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf()
				.disable();

		http
				.headers()
				.frameOptions()
				.disable();

		http
				.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}