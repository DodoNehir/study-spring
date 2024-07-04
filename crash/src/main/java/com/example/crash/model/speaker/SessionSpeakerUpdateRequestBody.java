package com.example.crash.model.speaker;

public record SessionSpeakerUpdateRequestBody(
    String company,
    String name,
    String description
) {

}
