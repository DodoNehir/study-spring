package com.example.crash.model.registration;

import com.example.crash.model.crashsession.CrashSession;
import com.example.crash.model.entity.RegistrationEntity;
import com.example.crash.model.user.User;
import jakarta.validation.constraints.NotEmpty;

public record Registration(
    @NotEmpty Long registrationId,
    @NotEmpty User user,
    @NotEmpty CrashSession crashSession) {

  public static Registration from(RegistrationEntity registrationEntity) {
    return new Registration(
        registrationEntity.getRegistrationId(),
        com.example.crash.model.user.User.from(registrationEntity.getUserEntity()),
        CrashSession.from(registrationEntity.getCrashSessionEntity()));
  }

}
