package com.example.testboard.model.user;

import com.example.testboard.model.entity.UserEntity;

import java.time.ZonedDateTime;

public record User(
    Long userId,
    String username,
    String profile,
    String description,
    Long followersCount,
    Long followingsCount,
    ZonedDateTime createdDateTime,
    ZonedDateTime updatedDateTime,
    Boolean isFollowing) {

  public static User from(UserEntity userEntity) {
    return new User(
        userEntity.getUserId(),
        userEntity.getUsername(),
        userEntity.getProfile(),
        userEntity.getDescription(),
        userEntity.getFollowersCount(),
        userEntity.getFollowingsCount(),
        userEntity.getCreatedDateTime(),
        userEntity.getUpdatedDateTime(),
        null
    );
  }

  public static User from(UserEntity userEntity, boolean isFollowing) {
    return new User(
        userEntity.getUserId(),
        userEntity.getUsername(),
        userEntity.getProfile(),
        userEntity.getDescription(),
        userEntity.getFollowersCount(),
        userEntity.getFollowingsCount(),
        userEntity.getCreatedDateTime(),
        userEntity.getUpdatedDateTime(),
        isFollowing
    );
  }
}