package com.example.crash.config;

import com.example.crash.model.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      // 계속 Jwt auth filter 가 실행될 것이다.
      filterChain.doFilter(request, response);
    } catch (JwtException e) {
      // 에러 발생 시 response 에 쓰고자 하는 것들
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");

      // 다른 에러들과 같은 형식으로 보내주기 위해 생성
      ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage());

      // json 으로 응답을 주니까 !!
      ObjectMapper objectMapper = new ObjectMapper();
      String responseJson = objectMapper.writeValueAsString(errorResponse);
      response.getWriter().write(responseJson);

    }

  }
}
