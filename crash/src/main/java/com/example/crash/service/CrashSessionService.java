package com.example.crash.service;

import com.example.crash.exception.crashsession.SessionNotFoundException;
import com.example.crash.model.crashsession.CrashSession;
import com.example.crash.model.crashsession.CrashSessionCreateRequestBody;
import com.example.crash.model.crashsession.CrashSessionUpdateRequestBody;
import com.example.crash.model.entity.CrashSessionEntity;
import com.example.crash.model.entity.SessionSpeakerEntity;
import com.example.crash.repository.CrashSessionCacheRepository;
import com.example.crash.repository.CrashSessionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class CrashSessionService {

  private final CrashSessionRepository crashSessionRepository;
  private final SessionSpeakerService sessionSpeakerService;
  private final CrashSessionCacheRepository crashSessionCacheRepository;

  public CrashSessionService(CrashSessionRepository crashSessionRepository,
      SessionSpeakerService sessionSpeakerService,
      CrashSessionCacheRepository crashSessionCacheRepository) {
    this.crashSessionRepository = crashSessionRepository;
    this.sessionSpeakerService = sessionSpeakerService;
    this.crashSessionCacheRepository = crashSessionCacheRepository;
  }


  public List<CrashSession> getCrashSessions() {
    List<CrashSession> sessionsListCache = crashSessionCacheRepository.getCrashSessionsListCache();

    if (!ObjectUtils.isEmpty(sessionsListCache)) {
      return sessionsListCache;
    } else {
      List<CrashSession> crashSessionList = crashSessionRepository.findAll().stream()
          .map(CrashSession::from)
          .toList();
      crashSessionCacheRepository.setCrashSessionsListCache(crashSessionList);
      return crashSessionList;
    }
  }

  public CrashSession getCrashSessionBySessionId(Long sessionId) {
    return crashSessionCacheRepository.getCrashSessionCache(sessionId).orElseGet(
        () -> {
          CrashSession crashSession = CrashSession.from(
              getCrashSessionEntityBySessionId(sessionId));
          crashSessionCacheRepository.setCrashSessionCache(crashSession);
          return crashSession;
        }
    );
  }


  public CrashSessionEntity getCrashSessionEntityBySessionId(Long sessionId) {
    return crashSessionRepository.findById(sessionId)
        .orElseThrow(() -> new SessionNotFoundException(sessionId));
  }

  public CrashSession createCrashSession(CrashSessionCreateRequestBody requestBody) {

    // Speaker entity 필요함
    SessionSpeakerEntity speakerEntity = sessionSpeakerService.getSessionSpeakerEntityBySpeakerId(
        requestBody.speakerId());

    CrashSessionEntity crashSessionEntity = CrashSessionEntity.of(
        requestBody.title(),
        requestBody.body(),
        requestBody.category(),
        requestBody.dateTime(),
        speakerEntity);

    return CrashSession.from(crashSessionRepository.save(crashSessionEntity));
  }

  public CrashSession updateCrashSession(Long sessionId,
      CrashSessionUpdateRequestBody requestBody) {
    // 이미 있었어야 수정하든 삭제하든 가능함
    CrashSessionEntity sessionEntity = getCrashSessionEntityBySessionId(sessionId);

    // 각 항목이 null 일 수도 있으므로 확인해서 수정해줌
    if (!ObjectUtils.isEmpty(requestBody.title())) {
      sessionEntity.setTitle(requestBody.title());
    }
    if (!ObjectUtils.isEmpty(requestBody.body())) {
      sessionEntity.setBody(requestBody.body());
    }
    if (!ObjectUtils.isEmpty(requestBody.category())) {
      sessionEntity.setCategory(requestBody.category());
    }
    if (!ObjectUtils.isEmpty(requestBody.dateTime())) {
      sessionEntity.setDateTime(requestBody.dateTime());
    }
    if (!ObjectUtils.isEmpty(requestBody.speakerId())) {
      SessionSpeakerEntity speakerEntity = sessionSpeakerService.getSessionSpeakerEntityBySpeakerId(
          requestBody.speakerId());
      sessionEntity.setSpeaker(speakerEntity);
    }

    return CrashSession.from(crashSessionRepository.save(sessionEntity));
  }

  public void deleteCrashSession(Long crashSessionId) {
    CrashSessionEntity crashSessionEntity = getCrashSessionEntityBySessionId(crashSessionId);
    crashSessionRepository.delete(crashSessionEntity);
  }
}
