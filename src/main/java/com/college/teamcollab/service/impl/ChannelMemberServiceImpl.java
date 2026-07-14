package com.college.teamcollab.service.impl;


import com.college.teamcollab.dto.channel.ChannelResponse;
import com.college.teamcollab.dto.member.MemberResponse;
import com.college.teamcollab.entity.Channel;
import com.college.teamcollab.entity.ChannelMember;
import com.college.teamcollab.entity.ChannelRole;
import com.college.teamcollab.entity.User;
import com.college.teamcollab.exception.ChannelNotFoundException;
import com.college.teamcollab.mapper.ChannelMapper;
import com.college.teamcollab.mapper.ChannelMemberMapper;
import com.college.teamcollab.repo.ChannelMemberRepository;
import com.college.teamcollab.repo.ChannelRepository;
import com.college.teamcollab.repo.UserRepository;
import com.college.teamcollab.service.ChannelMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelMemberServiceImpl implements ChannelMemberService {

    private final ChannelMemberRepository channelMemberRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(
                ()-> new IllegalArgumentException(
                        "Authenticated user not found"
                )
        );
    }

    private Channel findChannelById(Long id){
        return channelRepository.findById(id)
                .orElseThrow(() ->
                        new ChannelNotFoundException(
                                "Channel not found with id: " + id
                        ));
    }

    private ChannelMember findMembership(Channel channel, User user){
        return channelMemberRepository.findByChannelAndUser(channel,user)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Membership not found"
                        ));
    }


    @Override
    public MemberResponse joinChannel(Long channelId) {
        Channel channel = findChannelById(channelId);
        User user = getCurrentUser();
        if(channelMemberRepository.existsByChannelAndUser(channel,user)){
            throw new IllegalArgumentException(
                    "Already a member"
            );
        }
        ChannelMember member = ChannelMember.builder()
                        .channel(channel)
                        .user(user)
                        .role(ChannelRole.MEMBER)
                        .build();

        member = channelMemberRepository.save(member);

        return ChannelMemberMapper.toResponse(member);
    }

    @Override
    public void leaveChannel(Long channelId) {
        Channel channel = findChannelById(channelId);
        User user = getCurrentUser();
        ChannelMember member=findMembership(channel,user);
        if(member.getRole() == ChannelRole.OWNER){
            throw new IllegalArgumentException(
                    "Channel owner cannot leave"
            );

        }
        channelMemberRepository.delete(member);

    }

    @Override
    public List<MemberResponse> getMembers(Long channelId) {
        Channel channel = findChannelById(channelId);
        List<ChannelMember> members = channelMemberRepository.findByChannel(channel);

        return members.stream()
                .map(ChannelMemberMapper::toResponse)
                .toList();
    }

    @Override
    public Page<ChannelResponse> getMyChannels(Pageable pageable) {

        User currentUser = getCurrentUser();

        return channelMemberRepository
                .findByUser(currentUser, pageable)
                .map(ChannelMember::getChannel)
                .map(ChannelMapper::toResponse);
    }

}
