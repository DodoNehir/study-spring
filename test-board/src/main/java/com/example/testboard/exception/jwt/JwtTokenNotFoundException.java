package com.example.testboard.exception.jwt;

import io.jsonwebtoken.JwtException;

public class JwtTokenNotFoundException extends JwtException {

  public JwtTokenNotFoundException() {
    super("Jwt token not found");
  }

  public JwtTokenNotFoundException(String message) {
    super("Jwt token not found. message: " + message);
  }
}
