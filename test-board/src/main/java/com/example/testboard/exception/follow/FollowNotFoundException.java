package com.example.testboard.exception.follow;

import com.example.testboard.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class FollowNotFoundException extends ClientErrorException {

  public FollowNotFoundException() {
    super(HttpStatus.NOT_FOUND, "Follow not found");
  }

}
