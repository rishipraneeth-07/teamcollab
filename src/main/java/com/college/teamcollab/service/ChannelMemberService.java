package com.college.teamcollab.service;


import com.college.teamcollab.dto.channel.ChannelResponse;
import com.college.teamcollab.dto.member.MemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ChannelMemberService {
    MemberResponse joinChannel(Long channelId);
    void leaveChannel(Long channelId);
    List<MemberResponse> getMembers(Long channelId);
    Page<ChannelResponse> getMyChannels(Pageable pageable);
}
