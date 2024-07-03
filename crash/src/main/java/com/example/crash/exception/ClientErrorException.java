package com.example.crash.exception;

import org.springframework.http.HttpStatus;

public class ClientErrorException  extends RuntimeException{

  private HttpStatus httpStatus;

  public ClientErrorException(HttpStatus httpStatus, String message) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

}
