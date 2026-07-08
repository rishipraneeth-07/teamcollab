package com.college.teamcollab.mapper;

import com.college.teamcollab.dto.message.MessageResponse;
import com.college.teamcollab.entity.Message;

public final class MessageMapper {
    private MessageMapper() {}

    public static MessageResponse toResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getSender().getId())
                .senderUsername(message.getSender().getUsername())
                .channelId(message.getChannel().getId())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt()).build();

    }
}
