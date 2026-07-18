package com.college.teamcollab.dto.chat;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    private Long channelId;
    private String content;
}
