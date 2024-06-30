package com.example.testboard.controller;

import com.example.testboard.model.entity.UserEntity;
import com.example.testboard.model.post.Post;
import com.example.testboard.model.user.User;
import com.example.testboard.model.user.UserAuthenticationResponse;
import com.example.testboard.model.user.UserLoginRequestBody;
import com.example.testboard.model.user.UserPatchRequestBody;
import com.example.testboard.model.user.UserSignUpRequestBody;
import com.example.testboard.service.PostService;
import com.example.testboard.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
  private final PostService postService;

  public UserController(UserService userService, PostService postService) {
    this.userService = userService;
    this.postService = postService;
  }

  @GetMapping
  public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String query) {
    // 검색어가 없을 때는 모든 유저 검색. 검색어가 있다면 해당 검색어로 유저 검색
    return ResponseEntity.ok(userService.getUsers(query));
  }

  @GetMapping("/{username}")
  public ResponseEntity<User> getUser(@PathVariable String username) {
    return ResponseEntity.ok(userService.getUser(username));
  }

  @PatchMapping("/{username}")
  public ResponseEntity<User> updateUser(
      @PathVariable String username,
      @RequestBody UserPatchRequestBody requestBody,
      Authentication authentication) {
    User user = userService.updateUser(username, requestBody,
        (UserEntity) authentication.getPrincipal());
    return ResponseEntity.ok(user);
  }

  @GetMapping("/{username}/posts")
  public ResponseEntity<List<Post>> getPostByUsername(
      @PathVariable String username) {
    List<Post> posts = postService.getPostByUsername(username);
    return ResponseEntity.ok(posts);
  }

  @PostMapping("/{username}/follows")
  public ResponseEntity<User> follow(
      @PathVariable String username,
      Authentication authentication) {
    // userEntity가 username을 follow
    var user = userService.follow(username, (UserEntity) authentication.getPrincipal());
    return ResponseEntity.ok(user);
  }

  @DeleteMapping("/{username}/follows")
  public ResponseEntity<User> unfollow(
      @PathVariable String username,
      Authentication authentication) {
    // userEntity 가 username 을 unfollow
    var user = userService.unfollow(username, (UserEntity) authentication.getPrincipal());
    return ResponseEntity.ok(user);
  }

  @GetMapping("/{username}/followers")
  public ResponseEntity<List<User>> getFollowers(
      @PathVariable String username) {
    return ResponseEntity.ok(userService.getFollowers(username));
  }

  @GetMapping("/{username}/followings")
  public ResponseEntity<List<User>> getFollowings(
      @PathVariable String username) {
    return ResponseEntity.ok(userService.getFollowings(username));
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
