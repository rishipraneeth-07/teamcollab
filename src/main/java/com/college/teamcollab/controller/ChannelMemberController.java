package com.college.teamcollab.controller;

import com.college.teamcollab.service.ChannelMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/channels")
public class ChannelMemberController {
    private final ChannelMemberService channelMemberService;
}
