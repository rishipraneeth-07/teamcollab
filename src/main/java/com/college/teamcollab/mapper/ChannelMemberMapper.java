package com.college.teamcollab.mapper;

import com.college.teamcollab.dto.member.MemberResponse;
import com.college.teamcollab.entity.ChannelMember;

public final class ChannelMemberMapper {
    private ChannelMemberMapper() {}

    public static MemberResponse toResponse(ChannelMember member) {
        return MemberResponse.builder()
                .id(member.getId())
                .username(member.getUser().getUsername())
                .role(member.getRole())
                .joinedAt(member.getJoinedAt())
                .build();

    }

}
