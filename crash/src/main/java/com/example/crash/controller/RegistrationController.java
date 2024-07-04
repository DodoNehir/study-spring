package com.example.crash.controller;

import com.example.crash.model.entity.UserEntity;
import com.example.crash.model.registration.Registration;
import com.example.crash.model.registration.RegistrationCreateRequestBody;
import com.example.crash.service.RegistrationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registrations")
public class RegistrationController {

  private final RegistrationService registrationService;

  public RegistrationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }


  @GetMapping
  public ResponseEntity<List<Registration>> getRegistrations(Authentication authentication) {
    return ResponseEntity.ok(registrationService.getRegistraions(
        (UserEntity) authentication.getPrincipal()));
  }

  @GetMapping("/{registrationId}")
  public ResponseEntity<Registration> getRegistrationByRegistrationId(
      @PathVariable Long registrationId, Authentication authentication
  ) {
    return ResponseEntity.ok(registrationService.getRegistrationByRegistrationId(registrationId,
        (UserEntity) authentication.getPrincipal()));
  }


  @PostMapping
  public ResponseEntity<Registration> createRegistration(
      @RequestBody @Valid RegistrationCreateRequestBody requestBody,
      Authentication authentication
  ) {
    return ResponseEntity.ok(registrationService.createRegistration(requestBody,
        (UserEntity) authentication.getPrincipal()));
  }


  @DeleteMapping("/{registrationId}")
  public ResponseEntity<Void> deleteRegistration(
      @PathVariable Long registrationId,
      Authentication authentication
  ) {
    registrationService.deleteRegistration(registrationId,
        (UserEntity) authentication.getPrincipal());
    return ResponseEntity.noContent().build();
  }

}
