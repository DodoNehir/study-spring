package com.example.chatservice.controller;

import com.example.chatservice.dto.ChatMessage;
import java.security.Principal;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class StompChatController {

  // 어떤 경로로 pubhlish된 메세지를 라우팅 할 건지 지정해준다.
  @MessageMapping("/chats/{chatroomId}") // /pub/chats
  @SendTo("/sub/chats/{chatroomId}") // 구독자들의 경로로 보내준다.
  public ChatMessage handleMessage(@AuthenticationPrincipal Principal principal,
      @DestinationVariable Long chatroomId,
      @Payload Map<String, String> payload) {
    log.info("{} sent {} in {}", principal.getName(), payload, chatroomId);

    return new ChatMessage(principal.getName(), payload.get("message"));
  }

}
