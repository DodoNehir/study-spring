package com.example.crash.config;

import com.example.crash.service.JwtService;
import com.example.crash.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserService userService;

  public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
    this.jwtService = jwtService;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    // JWT 인증 로직
    // header의 authorize값을 봐야 하느네 Bearer prefix를 붙여서 오기 때문에
    String BEARER_PREFIX = "Bearer ";
    String authorization = request.getHeader("Authorization");

    // Spring Security의 security context에 담겨 있는 인증 정보가 null인 경우에 한해서 여기의 인증 로직을 수행하도록 한다.
    SecurityContext securityContext = SecurityContextHolder.getContext();

    if (!ObjectUtils.isEmpty(authorization)
        && authorization.startsWith(BEARER_PREFIX)
        && securityContext.getAuthentication() == null) {
      String accessToken = authorization.substring(BEARER_PREFIX.length());
      String username = jwtService.getUsernameFromToken(accessToken);
      UserDetails userDetails = userService.loadUserByUsername(username);

      // UsernamePasswordAuthenticationToken
      // 1. 사용자 인증정보 (principal)
      // 2. 비밀번호 (credential). 근데 비밀번호를 저장해서 사용하진 않으니 null
      // 3. Spring Security 에서 인증된 사용자를 대상으로 권한 처리를 하기 위해 authority 정보를 전달해줘야 한다.
      //    여기에는 userEntity 를 만들때 오버라이드했던 getAuthority 로 해당 유저의 Authority 를 전달한다.
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());

      // HTTP 요청에 대한 것도 authenticationToken 에 저장
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      // security context 에 authenticationToken 등록
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    // 무조건 추가
    filterChain.doFilter(request, response);
  }
}
