package org.ietdavv.alumni_portal.security;

import org.ietdavv.alumni_portal.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final MyUserDetailsService service;
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(MyUserDetailsService service, JwtFilter jwtFilter) {
        this.service = service;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173", "https://stable-tarpon-fine.ngrok-free.app")); // Allow your frontend origin
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow specific methods
        config.setAllowedHeaders(List.of("*")); // Allow all headers
        config.setAllowCredentials(true); // If using credentials (optional)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply CORS config to all endpoints
        return source;
    }

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers("/api/login").permitAll()
                                .requestMatchers("/api/register").permitAll()
                                .requestMatchers("/chat**").permitAll()
                                .requestMatchers("/styles.css", "/script.js", "/index.html").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }

}
