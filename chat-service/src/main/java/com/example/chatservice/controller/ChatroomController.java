package com.example.chatservice.controller;

import com.example.chatservice.entity.Chatroom;
import com.example.chatservice.service.ChatService;
import com.example.chatservice.vos.CustomOAuth2User;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/chats")
public class ChatroomController {

  private final ChatService chatService;

  public ChatroomController(ChatService chatService) {
    this.chatService = chatService;
  }

  @PostMapping
  public Chatroom createChatroom(@AuthenticationPrincipal CustomOAuth2User user,
      @RequestParam String title) {
    return chatService.createChatroom(user.getMember(), title);
  }

  @PostMapping("/{chatroomId}")
  public Boolean joinChatroom(@AuthenticationPrincipal CustomOAuth2User user ,@PathVariable Long chatroomId) {
    return chatService.joinChatroom(user.getMember(), chatroomId);
  }

  @DeleteMapping("/{chatroomId}")
  public Boolean leaveChatroom(@AuthenticationPrincipal CustomOAuth2User user,@PathVariable Long chatroomId) {
    return chatService.leaveChatroom(user.getMember(), chatroomId);
  }

  @GetMapping
  public List<Chatroom> getChatrooms(@AuthenticationPrincipal CustomOAuth2User user) {
    return chatService.getAllChatrooms(user.getMember());
  }

}
