package com.jkpark.study.security.util;

import com.jkpark.study.security.context.UserContext;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtFactory {
	// TODO : config 로 빼기
	private static final long ExpiredTime = 864000000;
	private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public String generateToken(UserContext account) {
		String jws = null;

		// TODO : try resource 로 바꿔보기
		try {
			jws = Jwts.builder()
					.setIssuer("jkpark")
					.setHeaderParam("typ", "JWT")
					.setId(account.getUser().getId())
					.claim("role", account.getUser().getRole())
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + ExpiredTime))
					.signWith(key).compact();
		} catch(Exception e) {
			log.error(e.getMessage());
		} finally {
			return jws;
		}
	}
}