package com.college.teamcollab.dto.message;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
    private Long id;
    private String content;
    private Long senderId;
    private String senderUsername;
    private Long channelId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
