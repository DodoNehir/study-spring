package com.example.crash.model.crashsession;

import jakarta.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;

public record CrashSessionCreateRequestBody(
    @NotEmpty String title,
    @NotEmpty String body,
    @NotEmpty CrashSessionCategory category,
    @NotEmpty ZonedDateTime dateTime,
    @NotEmpty Long speakerId) {

}
