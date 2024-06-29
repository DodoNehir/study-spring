package com.example.testboard.service;

import com.example.testboard.exception.user.UserAlreadyExistsException;
import com.example.testboard.exception.user.UserNotFoundException;
import com.example.testboard.model.entity.UserEntity;
import com.example.testboard.model.user.User;
import com.example.testboard.model.user.UserAuthenticationResponse;
import com.example.testboard.repository.UserEntityRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.authentication.BadCredentialsException;
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
  private JwtService jwtService;

  public UserService(
      UserEntityRepository userEntityRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      JwtService jwtService) {
    this.userEntityRepository = userEntityRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.jwtService = jwtService;
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

  public UserAuthenticationResponse authenticate(String username, String password) {
    // username 확인
    UserEntity userEntity = userEntityRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    // password 확인
    if (bCryptPasswordEncoder.matches(password, userEntity.getPassword())) {
      // jwt 생성
      String accessToken = jwtService.generateAccessToken(userEntity.getUsername());
      // UserAuthenticationResponse로 return
      return new UserAuthenticationResponse(accessToken);
    } else {
      throw new UserNotFoundException();
    }

  }

  public List<User> getUsers(String query) {
    List<UserEntity> userEntities;

    if (!query.isBlank()) {
      // TODO : user 검색
      userEntities = userEntityRepository.findByUsernameContaining(query);
    } else {
      // 모든 유저 검색
      userEntities = userEntityRepository.findAll();
    }

    return userEntities.stream().map(User::from).toList();
  }

  public User getUser(String username) {
    UserEntity userEntity = userEntityRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    return User.from(userEntity);
  }
}
