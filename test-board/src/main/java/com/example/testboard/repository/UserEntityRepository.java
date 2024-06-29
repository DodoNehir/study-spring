package com.example.testboard.repository;

import com.example.testboard.model.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    // containing은 일치가 아니라 포함하는 것들을 검색해준다.
    List<UserEntity> findByUsernameContaining(String username);
}
