package com.example.crash.exception.crashsession;

import com.example.crash.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class SessionNotFoundException extends ClientErrorException {

  public SessionNotFoundException() {
    super(HttpStatus.NOT_FOUND, "Session not found");
  }

  public SessionNotFoundException(Long sessionId) {
    super(HttpStatus.NOT_FOUND, "Crash session ID [" + sessionId + "] not found");
  }

  public SessionNotFoundException(String title) {
    super(HttpStatus.NOT_FOUND, "Crash session [" + title + "] not found");
  }


}
