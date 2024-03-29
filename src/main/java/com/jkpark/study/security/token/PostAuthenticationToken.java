package com.jkpark.study.security.token;

import com.jkpark.study.security.context.UserContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class PostAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private PostAuthenticationToken(
			Object principal,
			Object credentials,
			Collection<? extends GrantedAuthority> authorities
	) {
		super(principal, credentials, authorities);
	}

	public static PostAuthenticationToken getTokenFormAccountContext(User context) {

		return new PostAuthenticationToken(
				context,
				context.getPassword(),
				context.getAuthorities()
		);
	}

	public UserContext getAccountContext() {
		return (UserContext)super.getPrincipal();
	}
}
