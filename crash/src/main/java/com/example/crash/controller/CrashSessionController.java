package com.example.crash.controller;

import com.example.crash.model.crashsession.CrashSession;
import com.example.crash.model.crashsession.CrashSessionCreateRequestBody;
import com.example.crash.model.crashsession.CrashSessionRegistartionStatus;
import com.example.crash.model.crashsession.CrashSessionUpdateRequestBody;
import com.example.crash.model.entity.UserEntity;
import com.example.crash.service.CrashSessionService;
import com.example.crash.service.RegistrationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/crash-sessions")
public class CrashSessionController {

  private final CrashSessionService crashSessionService;
  private final RegistrationService registrationService;

  public CrashSessionController(CrashSessionService crashSessionService,
      RegistrationService registrationService) {
    this.crashSessionService = crashSessionService;
    this.registrationService = registrationService;
  }


  // GET은 권한이 필요 없지만 CUD의 경우엔 관리자 권한 체크 필요
  @GetMapping
  public ResponseEntity<List<CrashSession>> getCrashSessions() {
    return ResponseEntity.ok(crashSessionService.getCrashSessions());
  }

  @GetMapping("/{crashSessionId}")
  public ResponseEntity<CrashSession> getCrashSessionBySessionId(
      @PathVariable Long crashSessionId
  ) {
    return ResponseEntity.ok(crashSessionService.getCrashSessionBySessionId(crashSessionId));
  }

  @GetMapping("/{crashSessionId}/registration-status")
  public ResponseEntity<CrashSessionRegistartionStatus> getCrashSessionRegistrationStatusBySessionId(
      @PathVariable Long crashSessionId,
      Authentication authentication
  ) {

    CrashSessionRegistartionStatus status = registrationService.getRegistrationStatusBySessionIdAndUserId(
        crashSessionId,
        (UserEntity) authentication.getPrincipal());

    return ResponseEntity.ok(status);
  }


  @PostMapping
  public ResponseEntity<CrashSession> createCrashSession(
      @RequestBody @Valid CrashSessionCreateRequestBody requestBody
  ) {
    return ResponseEntity.ok(crashSessionService.createCrashSession(requestBody));
  }

  @PatchMapping("/{crashSessionId}")
  public ResponseEntity<CrashSession> updateCrashSession(
      @PathVariable Long crashSessionId,
      @RequestBody CrashSessionUpdateRequestBody requestBody
  ) {
    return ResponseEntity.ok(
        crashSessionService.updateCrashSession(crashSessionId, requestBody));
  }

  @DeleteMapping("/{crashSessionId}")
  public ResponseEntity<Void> deleteCrashSession(
      @PathVariable Long crashSessionId
  ) {
    crashSessionService.deleteCrashSession(crashSessionId);
    return ResponseEntity.noContent().build();
  }

}
