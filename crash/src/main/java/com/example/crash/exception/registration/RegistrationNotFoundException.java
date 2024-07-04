package com.example.crash.exception.registration;

import com.example.crash.exception.ClientErrorException;
import com.example.crash.model.entity.UserEntity;
import org.springframework.http.HttpStatus;

public class RegistrationNotFoundException extends ClientErrorException {

  public RegistrationNotFoundException() {
    super(HttpStatus.NOT_FOUND, "Registration not found");
  }

  public RegistrationNotFoundException(Long registrationId) {
    super(HttpStatus.NOT_FOUND, "Registration ID [" + registrationId + "] not found");
  }

  public RegistrationNotFoundException(String username) {
    super(HttpStatus.NOT_FOUND, "User [" + username + "] is not register any sessions");
  }


}
