package com.example.testboard.exception.user;

import com.example.testboard.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserNotAllowedException extends ClientErrorException {

  public UserNotAllowedException() {
    super(HttpStatus.FORBIDDEN, "User not allowed");
  }


}
