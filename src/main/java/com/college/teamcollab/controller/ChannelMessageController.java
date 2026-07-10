package com.college.teamcollab.controller;

import com.college.teamcollab.dto.message.CreateMessageRequest;
import com.college.teamcollab.dto.message.MessageResponse;
import com.college.teamcollab.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelMessageController {
    private final MessageService messageService;

    @PostMapping("/{channelId}/messages")
    public ResponseEntity<MessageResponse> sendMessage(
            @PathVariable Long channelId,
            @Valid @RequestBody CreateMessageRequest request) {

        return ResponseEntity.ok(
                messageService.sendMessage(channelId, request)
        );
    }

    @GetMapping("/{channelId}/messages")
    public ResponseEntity<Page<MessageResponse>> getMessages(@PathVariable Long channelId, Pageable pageable) {
        return ResponseEntity.ok(messageService.getMessages(channelId, pageable));
    }


}
