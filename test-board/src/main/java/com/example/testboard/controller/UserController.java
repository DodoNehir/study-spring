package com.example.testboard.controller;

import com.example.testboard.model.user.User;
import com.example.testboard.model.user.UserSignUpRequestBody;
import com.example.testboard.service.UserService;
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
  public ResponseEntity<User> signUp(@RequestBody UserSignUpRequestBody userSignUpRequestBody) {
    var newUser = userService.signUp(
        userSignUpRequestBody.username(),
        userSignUpRequestBody.password());

    return ResponseEntity.ok(newUser);
//    return new ResponseEntity<>(newUser, HttpStatus.OK);
  }

}
