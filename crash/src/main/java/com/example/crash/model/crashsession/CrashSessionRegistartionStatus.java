package com.example.crash.model.crashsession;

import com.fasterxml.jackson.annotation.JsonInclude;

// 값이 있을 때만 전달하도록
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CrashSessionRegistartionStatus(
    Long sessionId,
    boolean isRegistered,
    Long registrationId
) {

}
