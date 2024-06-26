package com.example.testboard.controller;

import com.example.testboard.model.Post;
import com.example.testboard.model.PostPostRequestBody;
import com.example.testboard.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok(postService.getPosts());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostByPostId(@PathVariable Long postId) {
        Optional<Post> matchingPost = postService.getPostByPostId(postId);

        return matchingPost
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostPostRequestBody postPostRequestBody) {
        var post = postService.createPost(postPostRequestBody);
        return ResponseEntity.ok(post);

    }
}
