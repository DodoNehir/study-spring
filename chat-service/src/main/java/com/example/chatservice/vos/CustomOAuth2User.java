package com.example.chatservice.vos;

import com.example.chatservice.entity.Member;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

  @Getter
  Member member;

  Map<String, Object> attributes;

  public CustomOAuth2User(Member member, Map<String, Object> attributes) {
    this.member = member;
    this.attributes = attributes;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return this.attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(() -> member.getRole());
  }

  @Override
  public String getName() {
    return member.getNickname();
  }
}
