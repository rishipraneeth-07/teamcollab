package com.college.teamcollab.service.impl;

import com.college.teamcollab.dto.message.CreateMessageRequest;
import com.college.teamcollab.dto.message.MessageResponse;
import com.college.teamcollab.dto.message.UpdateMessageRequest;
import com.college.teamcollab.entity.Channel;
import com.college.teamcollab.entity.Message;
import com.college.teamcollab.entity.User;
import com.college.teamcollab.exception.ChannelNotFoundException;
import com.college.teamcollab.exception.UnauthorizedChannelAccessException;
import com.college.teamcollab.mapper.MessageMapper;
import com.college.teamcollab.repo.ChannelMemberRepository;
import com.college.teamcollab.repo.ChannelRepository;
import com.college.teamcollab.repo.MessageRepository;
import com.college.teamcollab.repo.UserRepository;
import com.college.teamcollab.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final ChannelMemberRepository channelMemberRepository;

    private Channel findChannelById(Long channelId) {
        return channelRepository.findById(channelId)
                .orElseThrow(() ->
                        new ChannelNotFoundException(
                                "Channel not found with id: " + channelId));
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Authenticated user not found"));
    }

    @Override
    public MessageResponse sendMessage(Long channelId, CreateMessageRequest request) {
        Channel channel = findChannelById(channelId);
        User currentUser = getCurrentUser();
        if (!channelMemberRepository.existsByChannelAndUser(channel, currentUser)) {
            throw new UnauthorizedChannelAccessException(
                    "You are not a member of this channel"
            );
        }
        Message message = Message.builder()
                .content(request.getContent().trim())
                .sender(currentUser)
                .channel(channel).build();
        message=messageRepository.save(message);
        return MessageMapper.toResponse(message);
    }

    @Override
    public MessageResponse updateMessage(Long messageId, UpdateMessageRequest request) {

        return null;
    }


    @Override
    public void deleteMessage(Long messageId) {

    }

    @Override
    public Page<MessageResponse> getMessages(Long channelId, Pageable pageable) {
        return null;
    }
}
