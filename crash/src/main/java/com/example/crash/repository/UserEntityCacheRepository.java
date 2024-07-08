package com.example.crash.repository;

import com.example.crash.model.entity.UserEntity;
import java.time.Duration;
import java.util.Optional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserEntityCacheRepository {

  private final RedisTemplate<String, UserEntity> userEntityRedisTemplate;

  public UserEntityCacheRepository(RedisTemplate<String, UserEntity> redisTemplate) {
    this.userEntityRedisTemplate = redisTemplate;
  }

  public void setUserEntityCache(UserEntity userEntity) {
    var redisKey = getRedisKey(userEntity.getUsername());
    userEntityRedisTemplate.opsForValue().set(redisKey, userEntity, Duration.ofSeconds(30));
  }

  public Optional<UserEntity> getUserEntityCache(String username) {
    var userEntity = userEntityRedisTemplate.opsForValue().get(getRedisKey(username));

    return Optional.ofNullable(userEntity);
  }

  private String getRedisKey(String username) {
    return "user:" + username;
  }

}
