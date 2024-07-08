package com.example.crash.repository;

import com.example.crash.model.crashsession.CrashSession;
import com.example.crash.model.entity.UserEntity;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@Repository
public class CrashSessionCacheRepository {

  private final RedisTemplate<String, CrashSession> crashSessionRedisTemplate;
  private final RedisTemplate<String, List<CrashSession>> crashSessionsListRedisTemplate;

  public CrashSessionCacheRepository(RedisTemplate<String, CrashSession> crashSessionRedisTemplate,
      RedisTemplate<String, List<CrashSession>> crashSessionsListRedisTemplate) {
    this.crashSessionRedisTemplate = crashSessionRedisTemplate;
    this.crashSessionsListRedisTemplate = crashSessionsListRedisTemplate;
  }

  public void setCrashSessionCache(CrashSession crashSession) {
    var redisKey = getRedisKey(crashSession.sessionId());
    crashSessionRedisTemplate.opsForValue().set(redisKey, crashSession);
  }

  public void setCrashSessionsListCache(List<CrashSession> crashSessions) {
    crashSessionsListRedisTemplate.opsForValue().set("sessions", crashSessions);
  }

  public Optional<CrashSession> getCrashSessionCache(Long sessionId) {
    var crashSession = crashSessionRedisTemplate.opsForValue().get(getRedisKey(sessionId));

    return Optional.ofNullable(crashSession);
  }

  public List<CrashSession> getCrashSessionsListCache() {
    var crashSessions = crashSessionsListRedisTemplate.opsForValue().get("sessions");

    if (ObjectUtils.isEmpty(crashSessions)) {
      return List.of();
    } else {
      return crashSessions;
    }
  }

  private String getRedisKey(Long sessionId) {
    return "session:" + sessionId;
  }

}
