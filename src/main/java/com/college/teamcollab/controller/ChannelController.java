package com.college.teamcollab.controller;

import com.college.teamcollab.dto.channel.ChannelResponse;
import com.college.teamcollab.dto.channel.CreateChannelRequest;
import com.college.teamcollab.dto.channel.UpdateChannelRequest;
import com.college.teamcollab.service.ChannelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<ChannelResponse> updateChannel(
            @Valid @RequestBody UpdateChannelRequest request,@PathVariable Long id){

        return ResponseEntity.ok(channelService.updateChannel(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Long id){
        channelService.deleteChannel(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChannelResponse> getChannel(@PathVariable Long id){
        return ResponseEntity.ok(channelService.getChannelById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ChannelResponse>> getAllChannels(Pageable pageable){
        return  ResponseEntity.ok(channelService.getAllChannels(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ChannelResponse>>
    searchChannels(@RequestParam String keyword, Pageable pageable){
        return  ResponseEntity.ok(channelService.searchChannels(keyword, pageable));
    }

}
