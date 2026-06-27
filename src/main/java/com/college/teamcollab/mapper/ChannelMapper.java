package com.college.teamcollab.mapper;

import com.college.teamcollab.dto.channel.ChannelResponse;
import com.college.teamcollab.entity.Channel;

public final class ChannelMapper {
    private ChannelMapper() {
    }

    public static ChannelResponse toResponse(Channel channel) {

        return ChannelResponse.builder()
                .id(channel.getId())
                .name(channel.getName())
                .description(channel.getDescription())
                .createdByUsername(
                        channel.getCreatedBy().getUsername()
                )
                .createdAt(channel.getCreatedAt())
                .updatedAt(channel.getUpdatedAt())
                .build();

    }

}
