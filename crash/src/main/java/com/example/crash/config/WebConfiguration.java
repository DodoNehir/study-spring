package com.example.crash.config;

import com.example.crash.model.user.Role;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebConfiguration {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final JwtExceptionFilter jwtExceptionFilter;

  public WebConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
      JwtExceptionFilter jwtExceptionFilter) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.jwtExceptionFilter = jwtExceptionFilter;
  }

  // CORS 설정. Bean 으로 등록해준다.
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    // cors에 대한 설정
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:3000"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE"));
    configuration.setAllowedHeaders(List.of("*"));

    // 위에서 작성한 configuration 을 특정 url 패턴에서만 적용시킨다.
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/api/v1/**",
        configuration);
    return source;
  }

  // Security Filter Chain 설정. Bean 으로 등록해준다.
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // 먼저 cors, csrf 설정해준다.
    http
        // cors 기본설정
        .cors(Customizer.withDefaults())

        // 모든 request 허용
//        .authorizeHttpRequests((request) -> request.anyRequest().permitAll())

        // 회원가입, 로그인 request(POST)로 올 경우에만 무조건 허용하고
        // 그 외 api들은 인증정보를 확인할거야.
        .authorizeHttpRequests((requests) ->
            requests
                .requestMatchers(HttpMethod.POST, "/api/*/users", "/api/*/users/authenticate")
                .permitAll()

                // session speaker CUD 에는 ADMIN 권한이 필요해
                .requestMatchers(HttpMethod.GET, "/api/*/session-speakers",
                    "/api/*/session-speakers/**")
                .permitAll()
                .requestMatchers("/api/*/session-speakers", "/api/*/session-speakers/**")
                .hasAuthority(Role.ADMIN.name())

                .anyRequest()
                .authenticated())

        // 세션은 사용하지 않으니 꺼둠
        .sessionManagement(
            (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // 세션을 사용하지 않아서 csrf 설정도 끄기?
        .csrf(CsrfConfigurer::disable)

        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtExceptionFilter, jwtAuthenticationFilter.getClass())

        // spring security에서 기본적으로 제공하는 Basic 도 끄기
        .httpBasic(HttpBasicConfigurer::disable);

    return http.build();
  }
}
