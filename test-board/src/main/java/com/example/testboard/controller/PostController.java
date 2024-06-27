package com.example.testboard.controller;

import com.example.testboard.model.Post;
import com.example.testboard.model.PostPatchRequestBody;
import com.example.testboard.model.PostPostRequestBody;
import com.example.testboard.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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
        Optional<Post> matchingPost = postService.getPostByPostId(postId);

        return matchingPost
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostPostRequestBody postPostRequestBody) {
        logger.info("POST /api/v1/posts");
        var post = postService.createPost(postPostRequestBody);
        return ResponseEntity.ok(post);

    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Post> updatePostByPostId(@PathVariable Long postId,
                                                   @RequestBody PostPatchRequestBody postPatchRequestBody) {
        logger.info("PATCH /api/v1/posts/{}", postId);
        var matchingPost = postService.updatePost(postId, postPatchRequestBody);

        return ResponseEntity.ok(matchingPost); // 201 created 라는 코드가 있긴 하지만 내용 전달을 못 하므로 200을 사용한다.

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostByPostId(@PathVariable Long postId) {
        logger.info("DELETE /api/v1/posts/{}", postId);
        postService.deletePost(postId);

        return ResponseEntity.noContent().build(); // 204 noContent

    }
}
