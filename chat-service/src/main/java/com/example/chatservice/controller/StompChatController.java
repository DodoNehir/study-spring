package com.example.chatservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class StompChatController {

  // 어떤 경로로 pubhlish된 메세지를 라우팅 할 건지 지정해준다.
  @MessageMapping("/chats") // /pub/chats
  @SendTo("/sub") // 구독자들의 경로로 보내준다.
  public String handleMessage(@Payload String message) {
    log.info("{} reveived", message);

    return message;
  }

}
