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
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(
    name = "\"follow\"",
    indexes = {
        @Index(name = "follow_follower_following_idx", columnList = "follower, following", unique = true)
    })
public class FollowEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long followId;

  @Column
  private ZonedDateTime createdDateTime;

  @ManyToOne
  @JoinColumn(name = "follower")
  private UserEntity follower;

  @ManyToOne
  @JoinColumn(name = "following")
  private UserEntity following;

  public static FollowEntity of(UserEntity follower, UserEntity following) {
    FollowEntity followEntity = new FollowEntity();
    followEntity.setFollower(follower);
    followEntity.setFollowing(following);
    return followEntity;
  }

  @PrePersist
  private void prePersist() {
    this.createdDateTime = ZonedDateTime.now();
  }

}
