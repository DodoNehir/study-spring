package com.example.testboard.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.ZonedDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(
    name = "post",
    indexes = {@Index(name = "post_userid_idx", columnList = "userId")})
@SQLDelete(sql = "UPDATE \"post\" SET deleteddatetime = CURRENT_TIMESTAMP WHERE postId = ?")
// deprecated in Hibernate 6.3
// @Where(clause = "deleteddatetime IS NULL")
@SQLRestriction("deleteddatetime IS NULL")
public class PostEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId;

  @Column(columnDefinition = "TEXT")
  private String body;

  @Column
  private Long repliesCount = 0L;

  @Column
  private Long likesCount = 0L;

  @Column
  private ZonedDateTime createdDateTime;

  @Column
  private ZonedDateTime updatedDateTime;

  @Column
  private ZonedDateTime deletedDateTime;

  @ManyToOne
  @JoinColumn(name = "userId")
  private UserEntity user;

  public static PostEntity of(String body, UserEntity user) {
    PostEntity postEntity = new PostEntity();
    postEntity.body = body;
    postEntity.user = user;
    return postEntity;
  }
}
