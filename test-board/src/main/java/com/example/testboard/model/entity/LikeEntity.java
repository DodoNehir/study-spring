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
    name = "\"like\"",
    indexes = {
        @Index(name = "like_userid_postid_idx", columnList = "userId, postId", unique = true)
    })
// deprecated in Hibernate 6.3
// @Where(clause = "deleteddatetime IS NULL")
@SQLRestriction("deleteddatetime IS NULL")
public class LikeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long likeId;

  @Column
  private ZonedDateTime createdDateTime;

  @ManyToOne
  @JoinColumn(name = "userId")
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "postId")
  private PostEntity post;

  public static LikeEntity of(UserEntity userEntity, PostEntity postEntity) {
    LikeEntity replyEntity = new LikeEntity();
    replyEntity.user = userEntity;
    replyEntity.post = postEntity;

    return replyEntity;
  }

  @PrePersist
  private void prePersist() {
    this.createdDateTime = ZonedDateTime.now();
  }

}
