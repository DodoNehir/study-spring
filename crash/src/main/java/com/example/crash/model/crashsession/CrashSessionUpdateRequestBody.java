package com.example.crash.model.crashsession;

import com.example.crash.model.entity.SessionSpeakerEntity;
import java.time.ZonedDateTime;

public record CrashSessionUpdateRequestBody(
    String title,
    String body,
    CrashSessionCategory category,
    ZonedDateTime dateTime,
    Long speakerId) {

}
