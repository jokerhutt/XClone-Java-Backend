package com.twitter.twitter_clone_java;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final MessageController messageController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WebSocketHandler(MessageController messageController) {
        this.messageController = messageController;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
    	System.out.println("Connection established with session ID: " + session.getId());
        sessions.add(session);
    }
    
    

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("🔴 Connection closed: " + session.getId() + " Reason: " + status);
        sessions.remove(session);
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> messageData = objectMapper.readValue(message.getPayload(), Map.class);

        if (messageData.containsKey("type") && "heartbeat".equals(messageData.get("type"))) {
            System.out.println("💓 Received heartbeat from client");
            return;
        }

        messageController.message(messageData);
    }
    public void broadcastMessage(TextMessage message) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}