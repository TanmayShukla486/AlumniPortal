package org.ietdavv.alumni_portal.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.ietdavv.alumni_portal.entity.Message;
import org.ietdavv.alumni_portal.security.ChatSecurity;
import org.ietdavv.alumni_portal.service.ChatMessageService;
import org.ietdavv.alumni_portal.service.JwtService;
import org.ietdavv.alumni_portal.service.MyUserDetailsService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@AllArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;
    private final ApplicationContext context;
    private final JwtService jwtService;
    private final ChatMessageService chatMessageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = (String) session.getAttributes().get("username");
        String token = (String) session.getAttributes().get("token");
        UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
        if (token != null && jwtService.validateToken(token, userDetails)) {
            sessions.put(username, session);
        } else {
            session.close(CloseStatus.POLICY_VIOLATION);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String username = (String) session.getAttributes().get("username");
        String token = (String) session.getAttributes().get("token");
        try{
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            if (!jwtService.validateToken(token, userDetails)) {
                session.close(CloseStatus.POLICY_VIOLATION);
                return;
            }
        } catch (BeansException e) {
            System.out.println(e.getMessage());
        }


        Message unSaved = objectMapper.readValue(message.getPayload(), Message.class);
        if (!username.equals(unSaved.getSender())) {
            session.close(CloseStatus.POLICY_VIOLATION);
            return;
        }

        Message saved = chatMessageService.saveMessage(unSaved);
        WebSocketSession recipientSession = sessions.get(saved.getReceiver());
        if (recipientSession != null && recipientSession.isOpen()) {
            recipientSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(saved)));
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = (String) session.getAttributes().get("username");
        sessions.remove(username);
    }
}
