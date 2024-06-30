package com.example.testboard.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(
    name = "reply",
    indexes = {@Index(name = "reply_postid_idx", columnList = "postId"),
        @Index(name = "reply_userid_idx", columnList = "userId")})
@SQLDelete(sql = "UPDATE \"reply\" SET deleteddatetime = CURRENT_TIMESTAMP WHERE postId = ?")
// deprecated in Hibernate 6.3
// @Where(clause = "deleteddatetime IS NULL")
@SQLRestriction("deleteddatetime IS NULL")
public class ReplyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long replyId;

  @Column(columnDefinition = "TEXT")
  private String body;

  @Column
  private ZonedDateTime createdDateTime;

  @Column
  private ZonedDateTime updatedDateTime;

  @Column
  private ZonedDateTime deletedDateTime;

  @ManyToOne
  @JoinColumn(name = "userId")
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "postId")
  private PostEntity post;

  public static ReplyEntity of(String body, UserEntity userEntity, PostEntity postEntity) {
    ReplyEntity replyEntity = new ReplyEntity();
    replyEntity.body = body;
    replyEntity.user = userEntity;
    replyEntity.post = postEntity;

    return replyEntity;
  }

  @PrePersist
  private void prePersist() {
    this.createdDateTime = ZonedDateTime.now();
    this.updatedDateTime = this.createdDateTime;
  }

  @PreUpdate
  private void preUpdate() {
    this.updatedDateTime = ZonedDateTime.now();
  }
}
