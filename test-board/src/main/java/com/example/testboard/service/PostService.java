package com.example.testboard.service;

import com.example.testboard.model.Post;
import com.example.testboard.model.PostPatchRequestBody;
import com.example.testboard.model.PostPostRequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private static final List<Post> posts = new ArrayList<>();

    static {
        posts.add(new Post(1L, "post 1", ZonedDateTime.now()));
        posts.add(new Post(2L, "post 2", ZonedDateTime.now()));
        posts.add(new Post(3L, "post 3", ZonedDateTime.now()));
    }


    public List<Post> getPosts() {
        return posts;
    }

    public Optional<Post> getPostByPostId(Long postId) {
        return posts.stream().filter(post -> postId.equals(post.getPostId())).findFirst();
    }

    public Post createPost(PostPostRequestBody postPostRequestBody) {
        var newpostId = posts.stream().mapToLong(post -> post.getPostId()).max().orElse(0L) + 1;

        var newPost = new Post(newpostId, postPostRequestBody.body(), ZonedDateTime.now());

        posts.add(newPost);

        return newPost;
    }

    public Post updatePost(Long postId, PostPatchRequestBody postPatchRequestBody) {
        Optional<Post> postOptional = posts.stream().filter(post -> postId.equals(post.getPostId())).findFirst();

        if (postOptional.isPresent()) {
            var postToUpdate = postOptional.get();
            postToUpdate.setBody(postPatchRequestBody.body());
            return postToUpdate;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post");
        }
    }
}


