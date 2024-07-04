package com.example.crash.model.speaker;

import jakarta.validation.constraints.NotEmpty;

public record SessionSpeakerCreateRequestBody(
    @NotEmpty String company,
    @NotEmpty String name,
    @NotEmpty String description
) {

}
