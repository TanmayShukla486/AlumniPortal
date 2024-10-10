package org.ietdavv.alumni_portal.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.ietdavv.alumni_portal.service.MyUserDetailsService;
import org.ietdavv.alumni_portal.service.interfaces.JwtServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtServiceInterface service;
    private final ApplicationContext context;

    @Autowired
    public JwtFilter(JwtServiceInterface service, ApplicationContext context) {
        this.service = service;
        this.context = context;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getServletPath();
        if (requestPath.equals("/api/user/register") || requestPath.equals("/api/user/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = null;
        String username = null;
        log.debug(request.getHeader("Authorization"));
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            token = authHeader.substring(7);
            log.debug(token);
            username = service.extractUsername(token);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails user = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            if (service.validateToken(token, user)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
