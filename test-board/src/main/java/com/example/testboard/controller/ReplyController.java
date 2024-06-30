package com.example.testboard.controller;

import com.example.testboard.model.entity.UserEntity;
import com.example.testboard.model.reply.Reply;
import com.example.testboard.model.reply.ReplyPatchRequestBody;
import com.example.testboard.model.reply.ReplyPostRequestBody;
import com.example.testboard.service.ReplyService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/posts/{postId}/replies")
public class ReplyController {

  private final ReplyService replyService;

  public ReplyController(ReplyService replyService) {
    this.replyService = replyService;
  }

  // 게시물에 달린 모든 댓글을 가져오는 api
  @GetMapping
  public ResponseEntity<List<Reply>> getRepliesByPostId(@PathVariable Long postId) {
    List<Reply> replies = replyService.getRepliesByPostId(postId);
    return ResponseEntity.ok(replies);
  }


  // 댓글 생성
  @PostMapping
  public ResponseEntity<Reply> createReply(@PathVariable Long postId,
      @RequestBody ReplyPostRequestBody body,
      Authentication authentication) {
    Reply createdReply = replyService.createReply(postId, body,
        (UserEntity) authentication.getPrincipal());
    return ResponseEntity.ok(createdReply);
  }

  // 댓글 수정
  @PatchMapping("/{replyId}")
  public ResponseEntity<Reply> updateReply(@PathVariable Long postId,
      @PathVariable Long replyId,
      @RequestBody ReplyPatchRequestBody body,
      Authentication authentication) {
    Reply updateReply = replyService.updateReply(postId, replyId, body,
        (UserEntity) authentication.getPrincipal());

    return ResponseEntity.ok(updateReply);
  }

  // 댓글 삭제
  @DeleteMapping("/{replyId}")
  public ResponseEntity<Void> deleteReply(@PathVariable Long postId,
      @PathVariable Long replyId,
      Authentication authentication) {
    replyService.deleteReply(postId, replyId, (UserEntity) authentication.getPrincipal());

    return ResponseEntity.noContent().build();
  }

}
