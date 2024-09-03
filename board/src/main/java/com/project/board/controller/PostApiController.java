package com.project.board.controller;

import com.project.board.entity.PostRequestDto;
import com.project.board.entity.PostResponseDto;
import com.project.board.entity.SiteUser;
import com.project.board.service.PostService;
import com.project.board.service.SiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/api/post")
@RestController
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;
    private final SiteUserService siteUserService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getPostDtoList(@RequestParam("page") int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PostResponseDto> postPage = postService.getPostDtoList(pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("content", postPage.getContent());
        response.put("totalPages", postPage.getTotalPages());
        response.put("totalElements", postPage.getTotalElements());
        response.put("number", postPage.getNumber());
        response.put("size", postPage.getSize());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail/{id}")
    public PostResponseDto getPostDto(@PathVariable("id") Long id) {
        return this.postService.getPostDto(id);
    }

    @PostMapping("/create")
    public PostRequestDto createPost(@RequestBody PostRequestDto postRequestDto) {
        SiteUser siteUser = siteUserService.getSiteUserEntityByUsername(postRequestDto.getUsername());

        postService.createPost(postRequestDto, siteUser);

        return postRequestDto;
    }

    @PostMapping("/modify/{id}")
    public PostRequestDto modifyPost(@RequestBody PostRequestDto postRequestDto, @PathVariable("id") Long id) {
        postService.modifyPost(postRequestDto, id);
        return postRequestDto;
    }

    @GetMapping("/delete/{id}")
    public PostResponseDto deletePost(@PathVariable("id") Long id) {
        PostResponseDto postResponseDto = getPostDto(id);

        postService.deletePost(id);

        return postResponseDto;
    }
}
