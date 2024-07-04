package com.example.crash.exception.sessionspeaker;

import com.example.crash.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class SpeakerNotFoundException extends ClientErrorException {

  public SpeakerNotFoundException() {
    super(HttpStatus.NOT_FOUND, "Session speaker not found");
  }

  public SpeakerNotFoundException(Long speakerId) {
    super(HttpStatus.NOT_FOUND, "Session speaker ID [" + speakerId + "] not found");
  }

  public SpeakerNotFoundException(String name) {
    super(HttpStatus.NOT_FOUND, "Session speaker [" + name + "] not found");
  }


}
