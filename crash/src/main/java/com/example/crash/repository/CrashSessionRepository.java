package com.example.crash.repository;

import com.example.crash.model.entity.CrashSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrashSessionRepository extends JpaRepository<CrashSessionEntity, Long> {

}
