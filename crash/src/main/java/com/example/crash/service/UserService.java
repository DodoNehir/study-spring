package com.example.crash.service;

import com.example.crash.exception.user.UserAlreadyExistsException;
import com.example.crash.exception.user.UserNotFoundException;
import com.example.crash.model.entity.UserEntity;
import com.example.crash.model.user.User;
import com.example.crash.model.user.UserAuthenticationResponse;
import com.example.crash.model.user.UserLoginRequestBody;
import com.example.crash.model.user.UserSignupRequestBody;
import com.example.crash.repository.UserEntityCacheRepository;
import com.example.crash.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final UserEntityCacheRepository userEntityCacheRepository;

  public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
      JwtService jwtService, UserEntityCacheRepository userEntityCacheRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.userEntityCacheRepository = userEntityCacheRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return getUserEntityByUsername(username);
  }


  public User signUp(UserSignupRequestBody userSignupPostRequestBody) {
    // 회원가입 전에 먼저 이미 가입된 username 이 있는 지 확인
    String enterdUsername = userSignupPostRequestBody.username();

    userRepository
        .findByUsername(enterdUsername)
        .ifPresent(
            userEntity -> {
              throw new UserAlreadyExistsException(enterdUsername);
            });

    UserEntity newUserEntity = userRepository.save(
        UserEntity.of(
            userSignupPostRequestBody.username(),
            passwordEncoder.encode(userSignupPostRequestBody.password()),
            userSignupPostRequestBody.name(),
            userSignupPostRequestBody.email()
        ));

    return User.from(newUserEntity);

  }

  public UserAuthenticationResponse authenticate(UserLoginRequestBody userLoginRequestBody) {
    UserEntity userEntity = getUserEntityByUsername(userLoginRequestBody.username());

    if (!passwordEncoder.matches(userLoginRequestBody.password(), userEntity.getPassword())) {
      throw new UserNotFoundException(userLoginRequestBody.username());
    }

    // jwt 인증을 위한 accessToken을 만들어줘야 한다.
    String accessToken = jwtService.generateAccessToken(userEntity);
    return new UserAuthenticationResponse(accessToken);
  }


  // 공통 로직
  private User getUserByUsername(String username) {
    UserEntity userEntity = userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
    return User.from(userEntity);
  }

  private UserEntity getUserEntityByUsername(String username) {
    return userEntityCacheRepository.getUserEntityCache(username)
        .orElseGet(
            () -> {
              var userEntity = userRepository.findByUsername(username)
                  .orElseThrow(() -> new UserNotFoundException(username));
              userEntityCacheRepository.setUserEntityCache(userEntity);
              return userEntity;
            }
        );

  }

}
