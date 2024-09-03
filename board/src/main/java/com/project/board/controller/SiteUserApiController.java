package com.project.board.controller;

import com.project.board.entity.SiteUserRequestDto;
import com.project.board.service.SiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/siteuser")
public class SiteUserApiController {
    private final SiteUserService siteUserService;

    @PostMapping("/signup")
    public SiteUserRequestDto signup(@RequestBody SiteUserRequestDto siteUserRequestDto) {
        siteUserService.createSiteUser(siteUserRequestDto);

        return siteUserRequestDto;
    }
}
