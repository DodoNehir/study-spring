package com.example.chatservice.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

  // 누가 접속해있는지 저장하는 map.
  final Map<String, WebSocketSession> sessions = new HashMap<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    log.info("{} connected", session.getId());

    sessions.put(session.getId(), session);
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
      throws Exception {
    log.info("{} sent {}", session.getId(), message.getPayload());

    sessions.values().forEach(
        webSocketSession -> {
          try {
            webSocketSession.sendMessage(message);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    log.info("{} disconnected", session.getId());

    sessions.remove(session.getId());
  }
}
