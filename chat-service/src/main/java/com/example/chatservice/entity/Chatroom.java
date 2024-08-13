package com.example.chatservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Chatroom {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chatroom_id")
  @Id
  Long id;

  String title;

  @ManyToMany
  Set<Member> members;

  LocalDateTime createdAt;

}
