package com.example.testboard.controller;

import com.example.testboard.model.entity.UserEntity;
import com.example.testboard.model.post.Post;
import com.example.testboard.model.post.PostPatchRequestBody;
import com.example.testboard.model.post.PostPostRequestBody;
import com.example.testboard.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        logger.debug("Get /api/v1/posts");
        return ResponseEntity.ok(postService.getPosts());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostByPostId(@PathVariable Long postId) {
        logger.info("Get /api/v1/posts/{}", postId);
        Post postByPostId = postService.getPostByPostId(postId);

        return ResponseEntity.ok(postByPostId);

    }

    @PostMapping
    public ResponseEntity<Post> createPost(
        @RequestBody PostPostRequestBody postPostRequestBody,
        Authentication authentication) {
        logger.info("POST /api/v1/posts");
        var post = postService.createPost(postPostRequestBody, (UserEntity) authentication.getPrincipal());
        return ResponseEntity.ok(post);

    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Post> updatePostByPostId(@PathVariable Long postId,
                                                   @RequestBody PostPatchRequestBody postPatchRequestBody,
        Authentication authentication) {
        logger.info("PATCH /api/v1/posts/{}", postId);
        var post = postService.updatePost(postId, postPatchRequestBody, (UserEntity) authentication.getPrincipal());

        return ResponseEntity.ok(post); // 201 created 라는 코드가 있긴 하지만 내용 전달을 못 하므로 200을 사용한다.

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostByPostId(@PathVariable Long postId, Authentication authentication) {
        logger.info("DELETE /api/v1/posts/{}", postId);
        postService.deletePost(postId, (UserEntity) authentication.getPrincipal());

        return ResponseEntity.noContent().build(); // 204 noContent

    }
}
