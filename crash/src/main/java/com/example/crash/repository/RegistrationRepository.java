package com.example.crash.repository;

import com.example.crash.model.entity.CrashSessionEntity;
import com.example.crash.model.entity.RegistrationEntity;
import com.example.crash.model.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Long> {

  Optional<List<RegistrationEntity>> findAllByUserEntity(UserEntity userEntity);

  Optional<List<RegistrationEntity>> findAllByCrashSessionEntity(
      CrashSessionEntity crashSessionEntity);

  Optional<RegistrationEntity> findByCrashSessionEntityAndUserEntity(
      CrashSessionEntity crashSessionEntity, UserEntity userEntity);

  Optional<RegistrationEntity> findByRegistrationIdAndUserEntity(Long registrationId,
      UserEntity userEntity);

}
