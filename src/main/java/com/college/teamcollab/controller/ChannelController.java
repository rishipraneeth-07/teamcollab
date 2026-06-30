package com.college.teamcollab.controller;

import com.college.teamcollab.dto.channel.ChannelResponse;
import com.college.teamcollab.dto.channel.CreateChannelRequest;
import com.college.teamcollab.service.ChannelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping
    public ResponseEntity<ChannelResponse> createChannel(
            @Valid @RequestBody CreateChannelRequest request) {
        ChannelResponse response =
                channelService.createChannel(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }


}
