package com.example.testboard.service;

import com.example.testboard.exception.post.PostNotFoundException;
import com.example.testboard.exception.reply.ReplyNotFoundException;
import com.example.testboard.exception.user.UserNotAllowedException;
import com.example.testboard.exception.user.UserNotFoundException;
import com.example.testboard.model.entity.PostEntity;
import com.example.testboard.model.entity.ReplyEntity;
import com.example.testboard.model.entity.UserEntity;
import com.example.testboard.model.post.Post;
import com.example.testboard.model.post.PostPatchRequestBody;
import com.example.testboard.model.post.PostPostRequestBody;
import com.example.testboard.model.reply.Reply;
import com.example.testboard.model.reply.ReplyPatchRequestBody;
import com.example.testboard.model.reply.ReplyPostRequestBody;
import com.example.testboard.repository.PostEntityRepository;
import com.example.testboard.repository.ReplyEntityRepository;
import com.example.testboard.repository.UserEntityRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplyService {

  private final ReplyEntityRepository replyEntityRepository;
  private final PostEntityRepository postEntityRepository;
  private final UserEntityRepository userEntityRepository;

  public ReplyService(ReplyEntityRepository replyEntityRepository,
      PostEntityRepository postEntityRepository,
      UserEntityRepository userEntityRepository) {
    this.replyEntityRepository = replyEntityRepository;
    this.postEntityRepository = postEntityRepository;
    this.userEntityRepository = userEntityRepository;
  }


  public List<Reply> getRepliesByPostId(Long postId) {
    PostEntity postEntity = postEntityRepository.findById(postId)
        .orElseThrow(PostNotFoundException::new);
    List<ReplyEntity> replyEntities = replyEntityRepository.findByPost(postEntity);
    return replyEntities.stream().map(Reply::from).toList();
  }

  @Transactional
  public Reply createReply(Long postId, ReplyPostRequestBody replyPostRequestBody,
      UserEntity userEntity) {

    PostEntity postEntity = postEntityRepository.findById(postId)
        .orElseThrow(PostNotFoundException::new);

    ReplyEntity replyEntity = replyEntityRepository.save(
        ReplyEntity.of(replyPostRequestBody.body(), userEntity, postEntity)
    );

    postEntity.setRepliesCount(postEntity.getRepliesCount() + 1);
    postEntityRepository.save(postEntity);

    return Reply.from(replyEntity);
  }

  public Reply updateReply(Long postId, Long replyId, ReplyPatchRequestBody replyPatchRequestBody,
      UserEntity userEntity) {

    ReplyEntity replyEntity = replyEntityRepository.findById(replyId)
        .orElseThrow(() -> new ReplyNotFoundException(replyId));

    // user 확인
    if (!replyEntity.getUser().equals(userEntity)) {
      throw new UserNotAllowedException();
    }

    replyEntity.setBody(replyPatchRequestBody.body());

    ReplyEntity updatedReplyEntity = replyEntityRepository.save(replyEntity);
    return Reply.from(updatedReplyEntity);
  }

  @Transactional
  public void deleteReply(Long postId, Long replyId, UserEntity userEntity) {

    PostEntity postEntity = postEntityRepository.findById(postId)
        .orElseThrow(PostNotFoundException::new);

    ReplyEntity replyEntity = replyEntityRepository.findById(replyId)
        .orElseThrow(() -> new ReplyNotFoundException(replyId));

    if (!replyEntity.getUser().equals(userEntity)) {
      throw new UserNotAllowedException();
    }

    replyEntityRepository.delete(replyEntity);

    postEntity.setRepliesCount(Math.max(0, postEntity.getRepliesCount() - 1));
    postEntityRepository.save(postEntity);
  }

  public List<Reply> getReplies(String username) {
    UserEntity userEntity = userEntityRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    List<ReplyEntity> replies = replyEntityRepository.findByUser(userEntity);
    return replies.stream().map(Reply::from).toList();
  }
}


