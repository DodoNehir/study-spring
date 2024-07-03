package com.example.crash.controller;

import com.example.crash.model.user.User;
import com.example.crash.model.user.UserLoginRequestBody;
import com.example.crash.model.user.UserAuthenticationResponse;
import com.example.crash.model.user.UserSignupRequestBody;
import com.example.crash.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }


  @PostMapping
  public ResponseEntity<User> signUp(
      @Valid @RequestBody UserSignupRequestBody userSignupRequestBody) {
    User user = userService.signUp(userSignupRequestBody);
    return ResponseEntity.ok(user);
  }

  // id/pw를 받아서 가입 유저가 있다면 username을 바탕으로 accessToken을 만들어서 Response 로 내려준다.
  @PostMapping("/authenticate")
  public ResponseEntity<UserAuthenticationResponse> authenticate(
      @Valid @RequestBody UserLoginRequestBody userLoginRequestBody) {
    UserAuthenticationResponse response = userService.authenticate(userLoginRequestBody);
    return ResponseEntity.ok(response);
  }

}
