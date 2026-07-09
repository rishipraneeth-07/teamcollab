package com.college.teamcollab.service;

import com.college.teamcollab.dto.message.CreateMessageRequest;
import com.college.teamcollab.dto.message.MessageResponse;
import com.college.teamcollab.dto.message.UpdateMessageRequest;
import com.college.teamcollab.entity.Channel;
import com.college.teamcollab.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    MessageResponse sendMessage(Long channelId, CreateMessageRequest request);
    MessageResponse updateMessage(Long messageId, UpdateMessageRequest request);
    void deleteMessage(Long messageId);
    Page<MessageResponse> getMessages(Long channelId,Pageable pageable);

}
