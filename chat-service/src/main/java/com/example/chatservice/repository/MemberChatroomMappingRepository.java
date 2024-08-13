package com.example.chatservice.repository;

import com.example.chatservice.entity.MemberChatroomMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberChatroomMappingRepository extends
    JpaRepository<MemberChatroomMapping, Long> {

  Optional<MemberChatroomMapping> findByMemberIdAndChatroomId(Long memberId, Long chatroomId);

  void deleteByMemberIdAndChatroomId(Long memberId, Long chatroomId);

  List<MemberChatroomMapping> findAllByMemberId(Long memberId);

}
