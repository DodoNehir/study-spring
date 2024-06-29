package com.example.testboard.service;

import com.example.testboard.exception.post.PostNotFoundException;
import com.example.testboard.exception.user.UserNotFoundException;
import com.example.testboard.model.entity.PostEntity;
import com.example.testboard.model.entity.UserEntity;
import com.example.testboard.model.post.Post;
import com.example.testboard.model.post.PostPatchRequestBody;
import com.example.testboard.model.post.PostPostRequestBody;
import com.example.testboard.repository.PostEntityRepository;
import com.example.testboard.repository.UserEntityRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PostService {

  private final PostEntityRepository postEntityRepository;
  private final UserEntityRepository userEntityRepository;

  public PostService(PostEntityRepository postEntityRepository,
      UserEntityRepository userEntityRepository) {
    this.postEntityRepository = postEntityRepository;
    this.userEntityRepository = userEntityRepository;
  }

  public List<Post> getPosts() {
    return postEntityRepository.findAll().stream().map(Post::from).toList();
  }

  public Post getPostByPostId(Long postId) {

    PostEntity postEntity = postEntityRepository.findById(postId)
        .orElseThrow(PostNotFoundException::new);

    return Post.from(postEntity);
  }

  public Post createPost(PostPostRequestBody postPostRequestBody, UserEntity userEntity) {

    PostEntity savedPostEntity = postEntityRepository.save(
        PostEntity.of(postPostRequestBody.body(), userEntity)
    );
    return Post.from(savedPostEntity);
  }

  public Post updatePost(Long postId, PostPatchRequestBody postPatchRequestBody,
      UserEntity userEntity) {

    // post 확인
    PostEntity postEntity = postEntityRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException(postId));

    // user 확인
    if (!postEntity.getUser().equals(userEntity)) {
      throw new UserNotFoundException();
    }

    postEntity.setBody(postPatchRequestBody.body());
    PostEntity updatedPostEntity = postEntityRepository.save(postEntity);
    return Post.from(updatedPostEntity);
  }

  public void deletePost(Long postId, UserEntity userEntity) {
    PostEntity postEntity = postEntityRepository.findById(postId)
        .orElseThrow(PostNotFoundException::new);

    if (!postEntity.getUser().equals(userEntity)) {
      throw new UserNotFoundException();
    }

    postEntityRepository.delete(postEntity);
  }

  public List<Post> getPostByUsername(String username) {
    // user 존재 여부 조회
    UserEntity userEntity = userEntityRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    List<PostEntity> postEntities = postEntityRepository.findByUser(userEntity);

    return postEntities.stream().map(Post::from).toList();
  }
}


