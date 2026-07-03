package com.college.teamcollab.dto.member;

import com.college.teamcollab.entity.ChannelRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponse {
    private Long id;
    private String username;
    private ChannelRole role;
    private LocalDateTime joinedAt;
}
