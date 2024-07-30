package com.example.chatservice.config;

import com.example.chatservice.handlers.WebSocketChatHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@EnableWebSocket // 서버가 웹소켓을 사용할 수 있도록
@Configuration
public class WebSocketConfiguration implements WebSocketConfigurer {

  final WebSocketChatHandler webSocketChatHandler;

  public WebSocketConfiguration(WebSocketChatHandler webSocketChatHandler) {
    this.webSocketChatHandler = webSocketChatHandler;
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(webSocketChatHandler, "/ws/chats");
  }
}
