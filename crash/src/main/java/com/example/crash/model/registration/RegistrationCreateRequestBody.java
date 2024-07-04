package com.example.crash.model.registration;

import jakarta.validation.constraints.NotNull;

public record RegistrationCreateRequestBody(@NotNull Long sessionId) {

}
