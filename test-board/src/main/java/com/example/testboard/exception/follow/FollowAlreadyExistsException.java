package com.example.testboard.exception.follow;

import com.example.testboard.exception.ClientErrorException;
import com.example.testboard.model.entity.UserEntity;
import org.springframework.http.HttpStatus;

public class FollowAlreadyExistsException extends ClientErrorException {

    public FollowAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "Follow already exists");
    }

    public FollowAlreadyExistsException(UserEntity follower, UserEntity following) {
        super(HttpStatus.CONFLICT, follower.getUsername() +
            "is already following " + following.getUsername());
    }


}
