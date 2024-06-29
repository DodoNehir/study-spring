package com.example.testboard.service;

import com.example.testboard.exception.user.UserAlreadyExistsException;
import com.example.testboard.exception.user.UserNotFoundException;
import com.example.testboard.model.entity.UserEntity;
import com.example.testboard.model.user.User;
import com.example.testboard.repository.UserEntityRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private UserEntityRepository userEntityRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserService(
      UserEntityRepository userEntityRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userEntityRepository = userEntityRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userEntityRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
  }

  public User signUp(String username, String password) {
    // 이미 있는 지 확인
    userEntityRepository
        .findByUsername(username)
        .ifPresent(user -> {
          throw new UserAlreadyExistsException();
        });

    // savedUserEntity: JPA가 관리하는 객체. ID값도 있을 것임
    UserEntity savedUserEntity = userEntityRepository.save(
        UserEntity.of(username, bCryptPasswordEncoder.encode(password)));

    return User.from(savedUserEntity);
  }
}
