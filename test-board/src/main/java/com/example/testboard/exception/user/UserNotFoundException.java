package com.example.testboard.exception.user;

import com.example.testboard.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ClientErrorException {

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User not found");
    }

    public UserNotFoundException(String username) {
        super(HttpStatus.NOT_FOUND, "User name [" + username + "] is not found.");
    }


}
