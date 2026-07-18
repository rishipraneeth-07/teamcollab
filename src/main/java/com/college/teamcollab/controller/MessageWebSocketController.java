package com.college.teamcollab.controller;

import com.college.teamcollab.dto.chat.ChatMessage;
import com.college.teamcollab.dto.message.CreateMessageRequest;
import com.college.teamcollab.dto.message.MessageResponse;
import com.college.teamcollab.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageWebSocketController {
    private final MessageService  messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public void sendMessage(ChatMessage chatMessage) {
        CreateMessageRequest request =CreateMessageRequest
                .builder()
                .content(chatMessage.getContent())
                .build();

        MessageResponse response=messageService.sendMessage(chatMessage.getChannelId(), request);
        messagingTemplate.convertAndSend("/topic/channels"+chatMessage.getChannelId(), response);



    }

}
