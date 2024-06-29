package com.example.testboard.config;

import com.example.testboard.exception.jwt.JwtTokenNotFoundException;
import com.example.testboard.service.JwtService;
import com.example.testboard.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private JwtService jwtService;
  private UserService userService;

  public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
    this.jwtService = jwtService;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // JWT 검증

    // HTTP request 헤더에서 JWT토큰을 받아오기 위함
    String BEARER = "Bearer ";
    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    var securityContext = SecurityContextHolder.getContext();


    if (ObjectUtils.isEmpty(authorization) || !authorization.startsWith(BEARER)) {
      throw new JwtTokenNotFoundException();
    }

    // 정상적인 헤더값이 있고, 아직 인증 처리를 한 적이 없을 때
    if (!ObjectUtils.isEmpty(authorization)
        && authorization.startsWith(BEARER)
        && securityContext.getAuthentication() == null) {

      // JWT를 통해 username값을 추출하고 username을 통해 user정보도 조회한다.
      String accessToken = authorization.substring(BEARER.length());
      String username = jwtService.getUsername(accessToken);
      UserDetails userDetails = userService.loadUserByUsername(username);

      // 조회한 user를 http request 처리할 때(컨트롤러에서) 사용할 수 있도록 한다.
      // 인증된 정보를 SecurityContext에 설정해둬야 컨트롤러에서 해당 인증정보를 사용할 수 있다.
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      securityContext.setAuthentication(authenticationToken);
      SecurityContextHolder.setContext(securityContext);

    }

    // 검증 완료 후 이후 필터들이 정상 실행되도록
    filterChain.doFilter(request, response);
  }
}
