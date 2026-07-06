package com.college.teamcollab.service.impl;

import com.college.teamcollab.dto.channel.ChannelResponse;
import com.college.teamcollab.dto.channel.CreateChannelRequest;
import com.college.teamcollab.dto.channel.UpdateChannelRequest;
import com.college.teamcollab.entity.*;
import com.college.teamcollab.exception.ChannelAlreadyExistsException;
import com.college.teamcollab.exception.ChannelNotFoundException;
import com.college.teamcollab.exception.UnauthorizedChannelAccessException;
import com.college.teamcollab.mapper.ChannelMapper;
import com.college.teamcollab.repo.ChannelMemberRepository;
import com.college.teamcollab.repo.ChannelRepository;
import com.college.teamcollab.repo.UserRepository;
import com.college.teamcollab.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final ChannelMemberRepository channelMemberRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Authenticated user not found"));
    }

    private Channel findChannelById(Long channelId) {
        return channelRepository.findById(channelId)
                .orElseThrow(() ->
                        new ChannelNotFoundException(
                                "Channel not found with id: " + channelId));
    }

    private void validateOwnership(Channel channel) {
        User currentUser = getCurrentUser();

        boolean isCreator = channel.getCreatedBy()
                .getId()
                .equals(currentUser.getId());

        boolean isAdmin = currentUser.getRole() == Role.ADMIN;

        if (!isCreator && !isAdmin) {
            throw new UnauthorizedChannelAccessException(
                    "You are not authorized to perform this action");
        }
    }

    @Override
    public ChannelResponse createChannel(CreateChannelRequest request) {

        String name = request.getName().trim();

        if (channelRepository.existsByName(name)) {
            throw new ChannelAlreadyExistsException(
                    "Channel already exists"
            );
        }

        User currentUser = getCurrentUser();

        Channel channel = Channel.builder()
                .name(name)
                .description(request.getDescription())
                .createdBy(currentUser)
                .build();

        channel = channelRepository.save(channel);

        ChannelMember member = ChannelMember.builder()
                .channel(channel)
                .user(currentUser)
                .role(ChannelRole.OWNER)
                .build();

        channelMemberRepository.save(member);

        return ChannelMapper.toResponse(channel);
    }

    @Override
    public ChannelResponse updateChannel(Long channelId,UpdateChannelRequest request) {

        Channel channel = findChannelById(channelId);
        validateOwnership(channel);
        String name = request.getName().trim();
        if (!name.equals(channel.getName())
                && channelRepository.existsByName(name)) {

            throw new ChannelAlreadyExistsException(
                    "Channel already exists");
        }
        channel.setName(name);
        channel.setDescription(request.getDescription());
        channel = channelRepository.save(channel);
        return ChannelMapper.toResponse(channel);
    }

    @Override
    public void deleteChannel(Long channelId) {
        Channel channel = findChannelById(channelId);
        validateOwnership(channel);
        channelRepository.delete(channel);
    }

    @Override
    public ChannelResponse getChannelById(Long channelId) {
        return ChannelMapper.toResponse(
                findChannelById(channelId));
    }

    @Override
    public Page<ChannelResponse> getAllChannels(Pageable pageable) {
        return channelRepository.findAll(pageable)
                .map(ChannelMapper::toResponse);
    }

    @Override
    public Page<ChannelResponse> searchChannels(String keyword, Pageable pageable) {
        keyword = keyword.trim();
        return channelRepository
                .findByNameContainingIgnoreCase(
                        keyword,
                        pageable
                )
                .map(ChannelMapper::toResponse);
    }
}