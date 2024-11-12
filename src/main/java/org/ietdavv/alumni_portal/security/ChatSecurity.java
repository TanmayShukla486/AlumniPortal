package org.ietdavv.alumni_portal.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ietdavv.alumni_portal.service.JwtService;
import org.ietdavv.alumni_portal.service.MyUserDetailsService;
import org.springframework.context.ApplicationContext;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class ChatSecurity implements HandshakeInterceptor {

    private final JwtService jwtService;
    private final ApplicationContext context;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String token = extractToken(request);
        log.debug("token: {}", token);
        String username = jwtService.extractUsername(token);
        if (username == null) return false;
        UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
        if (token != null && jwtService.validateToken(token, userDetails)) {
            attributes.put("username", username);
            attributes.put("token", token);
            return true;
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // Nothing to be done
    }

    private String extractToken(ServerHttpRequest request) {
        String query = request.getURI().getQuery();
        System.out.println(query);
        if (query != null) {
            String[] params = query.split("&");
            for (String param: params) {
                if (param.startsWith("token="))
                    return param.substring(6);
            }
        }
        return null;
    }
}
