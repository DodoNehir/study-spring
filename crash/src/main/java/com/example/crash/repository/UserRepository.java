package com.example.crash.repository;

import com.example.crash.model.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>  {

  Optional<UserEntity> findByUsername(String username);

}
