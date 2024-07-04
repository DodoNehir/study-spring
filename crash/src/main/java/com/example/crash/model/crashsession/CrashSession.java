package com.example.crash.model.crashsession;

import com.example.crash.model.entity.CrashSessionEntity;
import com.example.crash.model.speaker.SessionSpeaker;
import java.time.ZonedDateTime;


public record CrashSession(
    Long sessionId,
    String title,
    String body,
    CrashSessionCategory category,
    ZonedDateTime dateTime,
    SessionSpeaker sessionSpeaker
) {

  public static CrashSession from(CrashSessionEntity crashSessionEntity) {
    return new CrashSession(
        crashSessionEntity.getSessionId(),
        crashSessionEntity.getTitle(),
        crashSessionEntity.getBody(),
        crashSessionEntity.getCategory(),
        crashSessionEntity.getDateTime(),
        SessionSpeaker.from(crashSessionEntity.getSpeaker())
    );
  }
}
