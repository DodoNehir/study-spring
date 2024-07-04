package com.example.crash.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(
    name = "registraiont",
    indexes = @Index(name = "registration_userid_sessionid_idx", columnList = "userid, sessionid", unique = true))
public class RegistrationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long registrationId;

  @ManyToOne
  @JoinColumn(name = "userid")
  private UserEntity userEntity;

  @ManyToOne
  @JoinColumn(name = "sessionid")
  private CrashSessionEntity crashSessionEntity;

  @Column
  private ZonedDateTime createdDateTime;

  public static RegistrationEntity of(UserEntity userEntity,
      CrashSessionEntity crashSessionEntity) {
    RegistrationEntity registraionEntity = new RegistrationEntity();
    registraionEntity.setUserEntity(userEntity);
    registraionEntity.setCrashSessionEntity(crashSessionEntity);
    return registraionEntity;
  }

  @PrePersist
  protected void onCreate() {
    createdDateTime = ZonedDateTime.now();
  }

}
