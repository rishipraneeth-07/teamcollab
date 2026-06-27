package com.college.teamcollab.service;

import com.college.teamcollab.dto.channel.ChannelResponse;
import com.college.teamcollab.dto.channel.CreateChannelRequest;
import com.college.teamcollab.dto.channel.UpdateChannelRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ChannelService {
    ChannelResponse createChannel(CreateChannelRequest request);
    ChannelResponse updateChannel(Long channelId, UpdateChannelRequest request);
    void deleteChannel(Long channelId);
    ChannelResponse getChannelById(Long channelId);
    Page<ChannelResponse> getAllChannels(Pageable pageable);
    Page<ChannelResponse> searchChannels(String keyword, Pageable pageable);

}
