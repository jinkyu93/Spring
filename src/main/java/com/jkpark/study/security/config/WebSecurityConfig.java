package com.jkpark.study.security.config;

import com.jkpark.study.security.filter.JwtFilter;
import com.jkpark.study.security.filter.LoginAuthenticationFilter;
import com.jkpark.study.security.handler.LoginAuthenticationFailureHandler;
import com.jkpark.study.security.handler.LoginAuthenticationSuccessHandler;
import com.jkpark.study.security.provider.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {
	private final String loginUrl = "/account/login";

	private LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
	private LoginAuthenticationFailureHandler loginAuthenticationFailureHandler;

	private JwtProvider jwtProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
                /**
                 * CookieCsrfTokenRepository.withHttpOnlyFalse())
                 * X-XSRF-TOKEN
                 * XSRF-TOKEN
                 */
                // cross-site request forgery
				.csrf(AbstractHttpConfigurer::disable)
                // X-Frame-Options 헤더 설정
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/account/**").permitAll() // 회원가입 등
                        .requestMatchers("/h2-console/**").permitAll() // h2-console 용
                        .requestMatchers("/actuator/**").permitAll() // actuator 용 // TODO : admin 권한 유저 만 조회할 수 있도록 수정 필요
                        .requestMatchers("/swagger-ui/**").permitAll() // swagger 용
                        .requestMatchers("/v3/api-docs/**").permitAll() // swagger 용
                        .anyRequest().authenticated()
                )
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(loginAuthenticationFilter(http.getSharedObject(AuthenticationManager.class)), JwtFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public LoginAuthenticationFilter loginAuthenticationFilter(AuthenticationManager authenticationManager) {
		AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(loginUrl, "POST");
		LoginAuthenticationFilter filter = new LoginAuthenticationFilter(antPathRequestMatcher, loginAuthenticationSuccessHandler, loginAuthenticationFailureHandler);
		filter.setAuthenticationManager(authenticationManager);
		return filter;
	}

	@Bean
	public JwtFilter jwtFilter() {
		return new JwtFilter(this.jwtProvider);
	}
}
