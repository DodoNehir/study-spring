package com.example.testboard.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      // 정상이면 필터 그대로 진행
      filterChain.doFilter(request, response);
    } catch (JwtException e) {
      // 에러면 어떤 에러인지 알려줌. security 필터는 GlobalExceptionHandler로 핸들링 못 함
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");

      // response message
      var errorMap = new HashMap<String, Object>();
      errorMap.put("status", HttpStatus.UNAUTHORIZED);
      errorMap.put("message", e.getMessage());

      ObjectMapper objectMapper = new ObjectMapper();
      String responseJson = objectMapper.writeValueAsString(errorMap);
      response.getWriter().write(responseJson);
    }

  }
}
