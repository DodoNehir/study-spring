package com.example.crash.repository;

import com.example.crash.model.entity.SessionSpeakerEntity;
import com.example.crash.model.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionSpeakerRepository extends JpaRepository<SessionSpeakerEntity, Long>  {

  Optional<SessionSpeakerEntity> findByName(String name);

}
