package com.example.crash.config;

import com.example.crash.model.crashsession.CrashSession;
import com.example.crash.model.entity.UserEntity;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CacheConfiguration {

  private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(
      new JavaTimeModule());

  // redis 에 접속
  @Bean
  RedisConnectionFactory redisConnectionFactory(
      @Value("${redis.host}") String redisHost,
      @Value("${redis.port}") int redisPort
  ) {
    // 구현체는 여러개인데 좀 더 최신에 만들어졌고 성능이 좋다고 하는 Lettuce
    // 기본 host 는 localhost, 포트는 6379. 만약 설정한다면 yaml 파일에 설정하게 된다.
    var config = new RedisStandaloneConfiguration(redisHost, redisPort);
    return new LettuceConnectionFactory(config);
  }

  @Bean
  public RedisTemplate<String, UserEntity> userEntityRedisTemplate(
      RedisConnectionFactory redisConnectionFactory
  ) {
    RedisTemplate<String, UserEntity> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(
        new Jackson2JsonRedisSerializer<UserEntity>(objectMapper, UserEntity.class));
    return template;
  }

  @Bean
  public RedisTemplate<String, CrashSession> crashSessionRedisTemplate(
      RedisConnectionFactory redisConnectionFactory
  ) {
    RedisTemplate<String, CrashSession> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(
        new Jackson2JsonRedisSerializer<CrashSession>(objectMapper, CrashSession.class));
    return template;
  }

  @Bean
  public RedisTemplate<String, List<CrashSession>> crashSessionsListRedisTemplate(
      RedisConnectionFactory redisConnectionFactory
  ) {
    RedisTemplate<String, List<CrashSession>> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(new StringRedisSerializer());

    JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, CrashSession.class);

    template.setValueSerializer(
        new Jackson2JsonRedisSerializer<List<CrashSession>>(objectMapper, type));
    return template;
  }

}
