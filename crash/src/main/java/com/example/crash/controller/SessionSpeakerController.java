package com.example.crash.controller;

import com.example.crash.model.speaker.SessionSpeaker;
import com.example.crash.model.speaker.SessionSpeakerCreateRequestBody;
import com.example.crash.model.speaker.SessionSpeakerUpdateRequestBody;
import com.example.crash.model.user.User;
import com.example.crash.model.user.UserAuthenticationResponse;
import com.example.crash.model.user.UserLoginRequestBody;
import com.example.crash.model.user.UserSignupRequestBody;
import com.example.crash.service.SessionSpeakerService;
import com.example.crash.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/session-speakers")
public class SessionSpeakerController {

  private final SessionSpeakerService sessionSpeakerService;

  public SessionSpeakerController(SessionSpeakerService sessionSpeakerService) {
    this.sessionSpeakerService = sessionSpeakerService;
  }


  // GET은 권한이 필요 없지만 CUD의 경우엔 관리자 권한 체크 필요
  @GetMapping
  public ResponseEntity<List<SessionSpeaker>> getSessionSpeakers() {
    return ResponseEntity.ok(sessionSpeakerService.getSessionSpeakers());
  }

  @GetMapping("/{sessionSpeakerId}")
  public ResponseEntity<SessionSpeaker> getSessionSpeakerBySpeakerId(
      @PathVariable Long sessionSpeakerId
  ) {
    return ResponseEntity.ok(sessionSpeakerService.getSessionSpeakerBySpeakerId(sessionSpeakerId));
  }


  @PostMapping
  public ResponseEntity<SessionSpeaker> createSessionSpeaker(
      @RequestBody @Valid SessionSpeakerCreateRequestBody requestBody
  ) {
    return ResponseEntity.ok(sessionSpeakerService.createSessionSpeaker(requestBody));
  }

  @PatchMapping("/{sessionSpeakerId}")
  public ResponseEntity<SessionSpeaker> updateSessionSpeaker(
      @PathVariable Long sessionSpeakerId,
      @RequestBody SessionSpeakerUpdateRequestBody requestBody
  ) {
    return ResponseEntity.ok(
        sessionSpeakerService.updateSessionSpeaker(sessionSpeakerId, requestBody));
  }

  @DeleteMapping("/{sessionSpeakerId}")
  public ResponseEntity<Void> deleteSessionSpeaker(
      @PathVariable Long sessionSpeakerId
  ) {
    sessionSpeakerService.deleteSessionSpeaker(sessionSpeakerId);
    return ResponseEntity.noContent().build();
  }

}
