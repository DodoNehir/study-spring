package com.example.chatservice.service;

import com.example.chatservice.entity.Member;
import com.example.chatservice.enums.Gender;
import com.example.chatservice.repository.MemberRepository;
import com.example.chatservice.vos.CustomOAuth2User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  final MemberRepository memberRepository;

  public CustomOAuth2UserService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)
      throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    Map<String, Object> attributeMap = oAuth2User.getAttribute("kakao_account");
    String email = (String) attributeMap.get("email");

    Member member = memberRepository.findByEmail(email)
        .orElseGet(() -> registerMember(attributeMap));

    return new CustomOAuth2User(member, oAuth2User.getAttributes());
  }

  private Member registerMember(Map<String, Object> attributeMap) {
    Member member = Member.builder()
        .email((String) attributeMap.get("email"))
        .nickname( (String) ((Map)attributeMap.get("profile")).get("nickname") )
        .name((String) attributeMap.get("name"))
        .phoneNumber((String) attributeMap.get("phoneNumber"))
        .gender(Gender.valueOf(((String) attributeMap.get("gender")).toUpperCase()))
        .birthday(getBirthday(attributeMap))
        .role("USER_ROLE")
        .build();

    return memberRepository.save(member);
  }

  private LocalDate getBirthday(Map<String, Object> attributeMap) {
    String birthYear = (String) attributeMap.get("birthyear");
    String birthday = (String) attributeMap.get("birthday");

    return LocalDate.parse(birthYear + birthday, DateTimeFormatter.BASIC_ISO_DATE);
  }


}
