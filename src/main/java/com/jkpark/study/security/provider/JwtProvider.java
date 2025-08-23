package com.jkpark.study.security.provider;

import com.jkpark.study.security.service.UserDetailsService;
import com.jkpark.study.security.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
public class JwtProvider {
	private final static String HEADER_AUTHORIZATION = "Authorization";

	private final JwtUtil jwtUtil;

	private final UserDetailsService userDetailsService;

	public String resolveJwtToken(HttpServletRequest request) {
		return request.getHeader(HEADER_AUTHORIZATION);
	}

	public boolean isTokenValid(String token) {
		if(token != null && jwtUtil.isTokenValid(token))
			return true;
		return false;
	}

	public Authentication getAuthentication(String token) {
		String userId = jwtUtil.getUserId(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
		return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
	}
}