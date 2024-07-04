package com.example.crash.model.entity;

import com.example.crash.model.user.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(
    name = "\"user\"",
    indexes = {
        @Index(name = "user_username_idx", columnList = "username", unique = true)
    })
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column
  @Enumerated(value = EnumType.STRING)
  private Role role;

  @Column
  private ZonedDateTime createdDateTime;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (role == Role.ADMIN) {
      return List.of(
          new SimpleGrantedAuthority("ROLE_ADMIN"),
          new SimpleGrantedAuthority(Role.ADMIN.name()),
          new SimpleGrantedAuthority("ROLE_USER"),
          new SimpleGrantedAuthority(Role.USER.name()));

    } else {
      return List.of(
          new SimpleGrantedAuthority("ROLE_USER"),
          new SimpleGrantedAuthority(Role.USER.name()));
    }
  }

  public static UserEntity of(String username, String password, String name, String email) {
    UserEntity user = new UserEntity();
    user.setUsername(username);
    user.setPassword(password);
    user.setName(name);
    user.setEmail(email);
    user.setRole(Role.USER);
    return user;
  }

  @PrePersist
  private void prePersist() {
    this.createdDateTime = ZonedDateTime.now();
  }

}
