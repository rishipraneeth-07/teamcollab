package com.college.teamcollab.service;


import com.college.teamcollab.dto.member.MemberResponse;


import java.util.List;

public interface ChannelMemberService {
    MemberResponse joinChannel(Long channelId);
    void leaveChannel(Long channelId);
    List<MemberResponse> getMembers(Long channelId);
}
