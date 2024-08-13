package com.example.chatservice.service;

import com.example.chatservice.entity.Chatroom;
import com.example.chatservice.entity.Member;
import com.example.chatservice.entity.MemberChatroomMapping;
import com.example.chatservice.repository.ChatroomRepository;
import com.example.chatservice.repository.MemberChatroomMappingRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatService {

  private final ChatroomRepository chatroomRepository;
  private final MemberChatroomMappingRepository memberChatroomMappingRepository;

  public ChatService(
      ChatroomRepository chatroomRepository,
      MemberChatroomMappingRepository memberChatroomMappingRepository
  ) {
    this.chatroomRepository = chatroomRepository;
    this.memberChatroomMappingRepository = memberChatroomMappingRepository;
  }

  // 채팅방 만들기
  public Chatroom createChatroom(Member member, String title) {
    Chatroom chatroom = Chatroom.builder()
        .title(title)
        .createdAt(LocalDateTime.now())
        .build();

    chatroom = chatroomRepository.save(chatroom);

    MemberChatroomMapping memberChatroomMapping = MemberChatroomMapping.builder()
        .member(member)
        .chatroom(chatroom)
        .build();

    memberChatroomMapping = memberChatroomMappingRepository.save(memberChatroomMapping);

    return chatroom;
  }

  // 채팅방 들어가기
  public Boolean joinChatroom(Member member, Long chatroomId) {
    // 참여 여부 확인
    if (memberChatroomMappingRepository.findByMemberIdAndChatroomId(member.getId(), chatroomId)
        .isPresent()) {
      log.info("이미 참여한 채팅방");
      return false;
    }

    Chatroom chatroom = chatroomRepository.findById(chatroomId).get();

    MemberChatroomMapping memberChatroomMapping = MemberChatroomMapping.builder()
        .member(member)
        .chatroom(chatroom)
        .build();

    memberChatroomMappingRepository.save(memberChatroomMapping);

    return true;
  }

  // 채팅방 나가기
  public Boolean leaveChatroom(Member member, Long chatroomId) {
    // 참여 여부 확인
    if (!memberChatroomMappingRepository.findByMemberIdAndChatroomId(member.getId(), chatroomId)
        .isPresent()) {
      log.info("이미 나간 채팅방");
      return false;
    }

    memberChatroomMappingRepository.deleteByMemberIdAndChatroomId(member.getId(), chatroomId);

    return true;
  }

  // 모든 채팅방 목록
  public List<Chatroom> getAllChatrooms(Member member) {
    List<MemberChatroomMapping> memberChatroomMappings = memberChatroomMappingRepository.findAllByMemberId(
        member.getId());

    return memberChatroomMappings.stream()
        .map(MemberChatroomMapping::getChatroom)
        .toList();
  }

}
