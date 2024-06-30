package com.example.testboard.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(
    name = "\"user\"",
    indexes = {@Index(name = "user_username_idx", columnList = "username", unique = true)})
@SQLDelete(sql = "UPDATE \"user\" SET deleteddatetime = CURRENT_TIMESTAMP WHERE userId = ?")
public class UserEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column
  private String profile;

  @Column
  private String description;

  @Column
  private Long followersCount = 0L;

  @Column
  private Long followingsCount = 0L;

  @Column
  private ZonedDateTime createdDateTime;

  @Column
  private ZonedDateTime updatedDateTime;

  @Column
  private ZonedDateTime deletedDateTime;


  public static UserEntity of(String username, String password) {
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(username);
    userEntity.setPassword(password);

    userEntity.setProfile(
        "https://avatar.iran.liara.run/public/" + (new Random().nextInt(100) + 1));

    return userEntity;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
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
