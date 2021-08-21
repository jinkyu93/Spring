package com.jkpark.study.security.provider;

import com.jkpark.study.security.service.UserDetailsService;
import com.jkpark.study.security.token.PostAuthenticationToken;
import com.jkpark.study.security.token.PreAuthenticationToken;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

	private UserDetailsService userDetailsService;

	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		PreAuthenticationToken token = (PreAuthenticationToken)authentication;

		String username = token.getUsername();
		String password = token.getUserPassword();

		User user = (User) userDetailsService.loadUserByUsername(username);

		if(isCorrectPassword(password, user)) {
			return PostAuthenticationToken.getTokenFormAccountContext(user);
		}

		// 이곳까지 통과하지 못하면 잘못된 요청으로 접근하지 못한것 그러므로 throw 해줘야 한다.
		throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return PreAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private boolean isCorrectPassword(String password, User user) {
		return passwordEncoder.matches(password, user.getPassword());
	}
}