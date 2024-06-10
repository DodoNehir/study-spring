package com.example.example4.controller;

import com.example.example4.domain.UserDto;
import com.example.example4.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/v1/users")
    public UserDto create(@RequestParam String name,
                          @RequestParam String gender) {
        return userService.create(name, gender);
    }

    @GetMapping("/api/v1/users/gender/{gender}")
    public List<UserDto> findByGender(@PathVariable String gender) {
        return userService.findByGender(gender);
    }

    @PutMapping("/api/v1/users/{id}")
    public UserDto updateName(@PathVariable int id, String newName) {
        return userService.updateName(id, newName);
    }

    @DeleteMapping("/api/v1/users/{id}")
    public UserDto delete(@PathVariable int id) {
        return userService.delete(id);
    }
}
