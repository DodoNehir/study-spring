package com.example.testboard.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UserSignUpRequestBody(

    @NotBlank
    String username,

    @NotBlank
    String password

) {

}
