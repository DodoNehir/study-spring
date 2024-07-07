package com.example.crash.service;

import com.example.crash.exception.registration.RegistrationAlreadyExistsException;
import com.example.crash.exception.registration.RegistrationNotFoundException;
import com.example.crash.model.crashsession.CrashSessionRegistartionStatus;
import com.example.crash.model.entity.CrashSessionEntity;
import com.example.crash.model.entity.RegistrationEntity;
import com.example.crash.model.entity.UserEntity;
import com.example.crash.model.registration.Registration;
import com.example.crash.model.registration.RegistrationCreateRequestBody;
import com.example.crash.repository.RegistrationRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

  private final RegistrationRepository registrationRepository;
  private final CrashSessionService crashSessionService;
  private final SlackService slackService;

  public RegistrationService(RegistrationRepository registrationRepository,
      CrashSessionService crashSessionService,
      SlackService slackService) {
    this.registrationRepository = registrationRepository;
    this.crashSessionService = crashSessionService;
    this.slackService = slackService;
  }


  public List<Registration> getRegistraions(UserEntity currentUser) {
    List<RegistrationEntity> registrationEntities = registrationRepository.findAllByUserEntity(
        currentUser).orElseThrow(() -> new RegistrationNotFoundException(currentUser.getName()));
    return registrationEntities.stream().map(Registration::from).toList();
  }


  public Registration getRegistrationByRegistrationId(Long registrationId, UserEntity currentUser) {
    return Registration.from(
        getRegistrationEntityByRegistrationIdAndUserEntity(registrationId, currentUser));
  }

  public Registration createRegistration(RegistrationCreateRequestBody requestBody,
      UserEntity currentUser) {
    // session 확인 후
    CrashSessionEntity sessionEntity = crashSessionService.getCrashSessionEntityBySessionId(
        requestBody.sessionId());

    // 이미 등록된 register 정보가 있는 지 확인 후
    registrationRepository
        .findByCrashSessionEntityAndUserEntity(sessionEntity, currentUser)
        .ifPresent(
            registration -> {
              throw new RegistrationAlreadyExistsException(
                  currentUser.getName(), sessionEntity.getTitle());
            });

    Registration registration = Registration.from(
        registrationRepository.save(RegistrationEntity.of(currentUser, sessionEntity)));
    slackService.sendSlackNotification(registration);

    return registration;
  }


  public RegistrationEntity getRegistrationEntityByRegistrationIdAndUserEntity(
      Long registrationId, UserEntity currentUser) {
    return registrationRepository
        .findByRegistrationIdAndUserEntity(registrationId, currentUser)
        .orElseThrow(() -> new RegistrationNotFoundException(registrationId));
  }

  public void deleteRegistration(Long registrationId, UserEntity currentUser) {
    RegistrationEntity registrationEntity = getRegistrationEntityByRegistrationIdAndUserEntity(
        registrationId, currentUser);
    registrationRepository.delete(registrationEntity);
  }

  public CrashSessionRegistartionStatus getRegistrationStatusBySessionIdAndUserId(
      Long crashSessionId, UserEntity currentUser) {

    CrashSessionEntity sessionEntity = crashSessionService.getCrashSessionEntityBySessionId(
        crashSessionId);

    Optional<RegistrationEntity> registrationEntity = registrationRepository
        .findByCrashSessionEntityAndUserEntity(sessionEntity, currentUser);

    return new CrashSessionRegistartionStatus(
        crashSessionId,
        registrationEntity.isPresent(),
        registrationEntity.map(RegistrationEntity::getRegistrationId).orElse(null));
  }
}
