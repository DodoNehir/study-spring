package com.example.chatservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class MemberChatroomMapping {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_chatroom_mapping_id")
  @Id
  Long id;

  @JoinColumn(name = "member_id")
  @ManyToOne
  Member member;

  @JoinColumn(name = "chatroom_id")
  @ManyToOne
  Chatroom chatroom;

}
