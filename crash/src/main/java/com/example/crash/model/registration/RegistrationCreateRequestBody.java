package com.example.crash.model.registration;

import jakarta.validation.constraints.NotEmpty;

public record RegistrationCreateRequestBody(@NotEmpty Long sessionId) {

}
