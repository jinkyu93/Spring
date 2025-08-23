package com.jkpark.study.security.util;

import com.jkpark.study.security.context.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
	// TODO : config 로 빼기 (application.yml)
	private static final long ExpiredTimeForAuthenticationToken = 14400000;
	private final SecretKey key = Jwts.SIG.HS256.key().build();

	public String generateAccessToken(UserContext userContext) {
		try {
			Date now = new Date();
			return Jwts.builder()
					.issuer("jkpark")
					.header().add("typ", "JWT").and()
					.subject(userContext.getAccountDTO().getId())
					.claim("role", userContext.getAccountDTO().getRole())
					.issuedAt(now)
					.expiration(new Date(now.getTime() + ExpiredTimeForAuthenticationToken))
					.signWith(key)
					.compact();
		} catch (Exception e) {
			log.error("Failed to generate access token", e);
			return null;
		}
	}

	public String generateRefreshToken(UserContext userContext) {
		// Refresh token implementation is out of scope for this fix.
		return "";
	}

	public String getUserId(String token) {
		return this.getClaims(token).getSubject();
	}

	public boolean isTokenValid(String token) {
		try {
			Date expiration = getClaims(token).getExpiration();
			return expiration.after(new Date());
		} catch (Exception e) {
			log.warn("Invalid JWT token received: {}", e.getMessage());
			return false;
		}
	}

	private Claims getClaims(String token) {
		return Jwts.parser()
				.verifyWith(this.key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}
