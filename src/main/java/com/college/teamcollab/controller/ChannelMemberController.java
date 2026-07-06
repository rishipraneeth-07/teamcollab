package com.college.teamcollab.controller;

import com.college.teamcollab.dto.member.MemberResponse;
import com.college.teamcollab.service.ChannelMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/channels")
public class ChannelMemberController {
    private final ChannelMemberService channelMemberService;

    @PostMapping("/{id}/join")
    public ResponseEntity<MemberResponse> joinChannel(@PathVariable Long id) {
        MemberResponse response=channelMemberService.joinChannel(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/leave")
    public ResponseEntity<Void> leaveChannel(@PathVariable Long id) {
        channelMemberService.leaveChannel(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<MemberResponse>> getMembers(@PathVariable Long id) {
        return ResponseEntity.ok(channelMemberService.getMembers(id)
        );
    }
}
