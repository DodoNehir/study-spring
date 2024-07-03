package com.example.crash.exception.user;

import com.example.crash.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ClientErrorException {

  public UserAlreadyExistsException() {
    super(HttpStatus.CONFLICT, "User is already exists.");
  }


  public UserAlreadyExistsException(String username) {
    super(HttpStatus.CONFLICT, "User [" + username + "] is already exists.");
  }


}
