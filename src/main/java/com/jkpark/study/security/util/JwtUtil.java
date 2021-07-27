package com.jkpark.study.security.util;

import com.jkpark.study.security.context.UserContext;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
	// TODO : config 로 빼기 (application.yml)
	private static final long ExpiredTimeForAuthenticationToken = 14400000;
	private static final long ExpiredTimeForRefreshToken = 864000000;
	private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public String generateAccessToken(UserContext userContext) {
		String jwtStr = null;

		// TODO : try resource 로 바꿔보기
		try {
			jwtStr = Jwts.builder()
					.setIssuer("jkpark")
					.setHeaderParam("typ", "JWT")
					.setId(userContext.getAccountDTO().getId())
					.claim("role", userContext.getAccountDTO().getRole())
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + ExpiredTimeForAuthenticationToken))
					.signWith(key).compact();
		} catch(Exception e) {
			log.error(e.getMessage());
		} finally {
			return jwtStr;
		}
	}

	public String generateRefreshToken(UserContext userContext) {
		return "";

		/*
		String jws = null;

		// TODO : try resource 로 바꿔보기
		try {
			jws = Jwts.builder()
					.setIssuer("jkpark")
					.setHeaderParam("typ", "JWT")
					.setId(userContext.getAccountDTO().getId())
					.claim("role", userContext.getAccountDTO().getRole())
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + ExpiredTimeForRefreshToken))
					.signWith(key).compact();
		} catch(Exception e) {
			log.error(e.getMessage());
		} finally {
			return jws;
		}
		 */
	}

	public String getUserId(String token) {
		return this.getClaims(token).getId();
	}

	public boolean isTokenValid(String token) {
		return this.getClaims(token).getExpiration().after(new Date());
	}

	private Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody();
	}
}