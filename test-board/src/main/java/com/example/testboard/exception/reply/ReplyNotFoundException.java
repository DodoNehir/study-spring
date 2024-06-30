package com.example.testboard.exception.reply;

import com.example.testboard.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class ReplyNotFoundException extends ClientErrorException {

  public ReplyNotFoundException() {
    super(HttpStatus.NOT_FOUND, "Reply not found");
  }

  public ReplyNotFoundException(Long replyId) {
    super(HttpStatus.NOT_FOUND, "Reply ID " + replyId + " is not found.");
  }

}
