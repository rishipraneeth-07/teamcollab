package com.college.teamcollab.service.impl;

import com.college.teamcollab.dto.message.CreateMessageRequest;
import com.college.teamcollab.dto.message.MessageResponse;
import com.college.teamcollab.dto.message.UpdateMessageRequest;
import com.college.teamcollab.entity.*;
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

    private User getCurrentUser(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Authenticated user not found"));
    }

    @Override
    public MessageResponse sendMessage(
            String email,
            Long channelId,
            CreateMessageRequest request
    ) {

        Channel channel = findChannelById(channelId);

        User currentUser = getCurrentUser(email);

        if (!channelMemberRepository.existsByChannelAndUser(channel, currentUser)) {
            throw new UnauthorizedChannelAccessException(
                    "You are not a member of this channel"
            );
        }

        Message message = Message.builder()
                .content(request.getContent().trim())
                .sender(currentUser)
                .channel(channel)
                .build();

        message = messageRepository.save(message);

        return MessageMapper.toResponse(message);
    }

    // ---------- REST APIs BELOW (UNCHANGED) ----------

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Authenticated user not found"));
    }

    private Message findMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Message not found with id: " + id));
    }

    @Override
    public MessageResponse updateMessage(
            Long messageId,
            UpdateMessageRequest request
    ) {

        Message message = findMessageById(messageId);

        User currentUser = getCurrentUser();

        if (!message.getSender().equals(currentUser)) {
            throw new UnauthorizedChannelAccessException(
                    "You can only edit your own message");
        }

        message.setContent(request.getContent());

        message = messageRepository.save(message);

        return MessageMapper.toResponse(message);
    }

    private boolean isChannelOwner(Channel channel, User user) {

        ChannelMember member =
                channelMemberRepository
                        .findByChannelAndUser(channel, user)
                        .orElse(null);

        return member != null &&
                member.getRole() == ChannelRole.OWNER;
    }

    @Override
    public void deleteMessage(Long messageId) {

        Message message = findMessageById(messageId);

        User currentUser = getCurrentUser();

        boolean isSender =
                message.getSender().getId().equals(currentUser.getId());

        boolean isOwner =
                isChannelOwner(message.getChannel(), currentUser);

        if (!isSender && !isOwner) {
            throw new UnauthorizedChannelAccessException(
                    "You are not authorized to delete this message");
        }

        messageRepository.delete(message);
    }

    @Override
    public Page<MessageResponse> getMessages(
            Long channelId,
            Pageable pageable
    ) {

        Channel channel = findChannelById(channelId);

        User currentUser = getCurrentUser();

        if (!channelMemberRepository.existsByChannelAndUser(channel, currentUser)) {
            throw new UnauthorizedChannelAccessException(
                    "You are not a member of this channel"
            );
        }

        Page<Message> messages =
                messageRepository.findByChannelOrderByCreatedAtAsc(
                        channel,
                        pageable
                );

        return messages.map(MessageMapper::toResponse);
    }
}