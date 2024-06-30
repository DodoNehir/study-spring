package com.example.testboard.exception.follow;

import com.example.testboard.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class InvalidFollowException extends ClientErrorException {

  public InvalidFollowException() {
    super(HttpStatus.BAD_REQUEST, "Invalid follow");
  }

  public InvalidFollowException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }

}
