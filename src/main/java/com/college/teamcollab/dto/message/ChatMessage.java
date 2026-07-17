package com.college.teamcollab.dto.message;

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
