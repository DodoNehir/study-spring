package com.example.example4.domain;

import lombok.Getter;

@Getter
public class UserDto {
    private String name;
    private String gender;

    UserDto(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public static UserDto of(User user) {
        return new UserDto(user.getName(), user.getGender());
    }
}
