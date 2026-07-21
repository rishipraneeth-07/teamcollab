package com.college.teamcollab.security;

import com.college.teamcollab.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println("--------------------------------");
        System.out.println("Command : " + accessor.getCommand());
        System.out.println("Session : " + accessor.getSessionId());
        System.out.println("User    : " + accessor.getUser());
        System.out.println("--------------------------------");
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            System.out.println("CONNECT intercepted");

            String authHeader = accessor.getFirstNativeHeader("Authorization");

            System.out.println("Header: " + authHeader);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                System.out.println("Header is valid");

                String token = authHeader.substring(7);

                System.out.println("Token extracted");

                boolean valid = jwtUtil.validateToken(token);

                System.out.println("JWT Valid: " + valid);

                if (valid) {

                    String email = jwtUtil.extractEmail(token);

                    System.out.println("Email: " + email);

                    UserDetails userDetails =
                            customUserDetailsService.loadUserByUsername(email);

                    System.out.println("User Loaded: " + userDetails.getUsername());

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    System.out.println("Authentication Created");

                    accessor.setUser(authentication);

// Store authenticated user information in the WebSocket session
                    accessor.getSessionAttributes().put("email", email);
                    accessor.getSessionAttributes().put("authentication", authentication);

                    System.out.println("User attached to session");
                    System.out.println("Stored email: " + email);
                }
            }
        }
        return message;
    }
}
