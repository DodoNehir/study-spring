package com.example.crash.exception.registration;

import com.example.crash.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class RegistrationAlreadyExistsException extends ClientErrorException {

  public RegistrationAlreadyExistsException() {
    super(HttpStatus.CONFLICT, "Registration is already exists");
  }

  public RegistrationAlreadyExistsException(String user, String session) {
    super(HttpStatus.CONFLICT,
        "User [" + user + "] is already registered with session [" + session + "]");
  }


}
