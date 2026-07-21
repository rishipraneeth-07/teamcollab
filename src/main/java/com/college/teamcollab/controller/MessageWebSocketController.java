package com.college.teamcollab.controller;

import com.college.teamcollab.dto.chat.ChatMessage;
import com.college.teamcollab.dto.message.CreateMessageRequest;
import com.college.teamcollab.dto.message.MessageResponse;
import com.college.teamcollab.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MessageWebSocketController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(
            ChatMessage chatMessage,
            @Header("simpSessionAttributes")
            Map<String, Object> sessionAttributes
    ) {

        String email = (String) sessionAttributes.get("email");

        CreateMessageRequest request = CreateMessageRequest.builder()
                .content(chatMessage.getContent())
                .build();

        MessageResponse response =
                messageService.sendMessage(
                        email,
                        chatMessage.getChannelId(),
                        request
                );

        messagingTemplate.convertAndSend(
                "/topic/channels/" + chatMessage.getChannelId(),
                response
        );
    }
}