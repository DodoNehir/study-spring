package com.example.testboard.model.post;

import com.example.testboard.model.entity.PostEntity;
import com.example.testboard.model.user.User;
import java.time.ZonedDateTime;


public record Post(
    Long postId,
    String body,
    User user,
    Long repliesCount,
    ZonedDateTime createdDateTime,
    ZonedDateTime updatedDateTime,
    ZonedDateTime deletedDateTime,
    Boolean isLiking
) {

  public static Post from(PostEntity postEntity) {
    return new Post(
        postEntity.getPostId(),
        postEntity.getBody(),
        User.from(postEntity.getUser()),
        postEntity.getRepliesCount(),
        postEntity.getCreatedDateTime(),
        postEntity.getUpdatedDateTime(),
        postEntity.getDeletedDateTime(),
        null
    );
  }

  public static Post from(PostEntity postEntity, boolean isLiking) {
    return new Post(
        postEntity.getPostId(),
        postEntity.getBody(),
        User.from(postEntity.getUser()),
        postEntity.getRepliesCount(),
        postEntity.getCreatedDateTime(),
        postEntity.getUpdatedDateTime(),
        postEntity.getDeletedDateTime(),
        isLiking
    );
  }


}
