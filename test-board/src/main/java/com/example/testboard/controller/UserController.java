package com.example.testboard.controller;

import com.example.testboard.model.user.User;
import com.example.testboard.model.user.UserAuthenticationResponse;
import com.example.testboard.model.user.UserLoginRequestBody;
import com.example.testboard.model.user.UserSignUpRequestBody;
import com.example.testboard.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String query) {
    // 검색어가 없을 때는 모든 유저 검색. 검색어가 있다면 해당 검색어로 유저 검색
    return ResponseEntity.ok(userService.getUsers(query));
  }

  @GetMapping("/{username}")
  public ResponseEntity<User> getUser(@PathVariable String username) {
    // 검색어가 없을 때는 모든 유저 검색. 검색어가 있다면 해당 검색어로 유저 검색
    return ResponseEntity.ok(userService.getUser(username));
  }

  @PostMapping
  public ResponseEntity<User> signUp(
      @Valid @RequestBody UserSignUpRequestBody userSignUpRequestBody) {
    var newUser = userService.signUp(
        userSignUpRequestBody.username(),
        userSignUpRequestBody.password());

    return ResponseEntity.ok(newUser);
//    return new ResponseEntity<>(newUser, HttpStatus.OK);
  }

  @PostMapping("/authenticate")
  public ResponseEntity<UserAuthenticationResponse> authenticate(
      @Valid @RequestBody UserLoginRequestBody userLoginRequestBody) {
    var response = userService.authenticate(
        userLoginRequestBody.username(),
        userLoginRequestBody.password());


    return ResponseEntity.ok(response);
  }

}
