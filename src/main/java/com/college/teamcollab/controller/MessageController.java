package com.college.teamcollab.controller;

import com.college.teamcollab.dto.message.MessageResponse;
import com.college.teamcollab.dto.message.UpdateMessageRequest;
import com.college.teamcollab.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @PutMapping("/{messageId}")
    public ResponseEntity<MessageResponse> updateMessage(
            @PathVariable Long messageId,@RequestBody @Valid UpdateMessageRequest updateMessageRequest){
        return ResponseEntity.ok(messageService.updateMessage(messageId, updateMessageRequest));
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId){
        messageService.deleteMessage(messageId);
        return ResponseEntity.noContent().build();
    }
}
