package com.example.crash.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class jwtService {

  private static final Logger logger = LoggerFactory.getLogger(jwtService.class);
  private final SecretKey key;

  public jwtService(@Value("${jwt.secret-key}") String secretKeyString) {
    key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyString));
  }

  public String generateAccessToken(UserDetails userDetails) {
    return generateToken(userDetails.getUsername());
  }

  public String getUsernameFromToken(String token) {
    return validateToken(token);
  }


  private String generateToken(String username) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60 * 3); // 3시간
    return Jwts.builder()
        .subject(username)
        .signWith(key)
        .issuedAt(now)
        .expiration(expiryDate)
        .compact();
  }

  private String validateToken(String token) {
    try {
      return Jwts.parser()
          .verifyWith(key)
          .build()
          .parseSignedClaims(token)
          .getPayload()
          .getSubject();
    } catch (JwtException e) {
      logger.error("JwtException", e);
      throw e;
    }
  }


}
