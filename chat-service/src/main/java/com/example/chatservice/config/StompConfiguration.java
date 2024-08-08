package com.example.chatservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker // stomp 기능 설정
@Configuration
public class StompConfiguration implements WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // web socket client 가 어떤 경로로 접근해야 하는 지 엔드포인트 지정
    registry.addEndpoint("/stomp/chats").withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    // 메세지 브로커로서의 역할을 위해서 클라이언트에서 메세지를 발행하고
    // 클라이언트는 메세지를 받기 위해 구독을 신청해야 한다. 그 경로를 지정해줘야 한다.

    // message publish url
    registry.setApplicationDestinationPrefixes("/pub");

    // subscribe
    registry.enableSimpleBroker("/sub");
  }
}
