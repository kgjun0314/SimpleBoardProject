package com.project.board.controller;

import com.project.board.entity.*;
import com.project.board.service.CommentService;
import com.project.board.service.PostService;
import com.project.board.service.SiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/api/comment")
@RestController
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentService commentService;
    private final PostService postService;
    private final SiteUserService siteUserService;

    @PostMapping("/create/{id}")
    public CommentRequestDto createComment(@PathVariable("id") Long id, @RequestBody CommentRequestDto commentRequestDto) {
        Post post = postService.getPostEntity(id);
        SiteUser siteUser = siteUserService.getSiteUserEntityByUsername(commentRequestDto.getUsername());

        Comment comment = commentService.createComment(post, siteUser, commentRequestDto);

        commentRequestDto.setCommentId(comment.getId());

        return commentRequestDto;
    }

    @GetMapping("/detail/{id}")
    public CommentResponseDto getCommentDto(@PathVariable("id") Long id) {
        return this.commentService.getCommentDto(id);
    }

    @PostMapping("/modify/{id}")
    public CommentRequestDto modifyComment(@RequestBody CommentRequestDto commentRequestDto, @PathVariable("id") Long id) {
        Comment comment = commentService.modifyComment(commentRequestDto, id);

        commentRequestDto.setCommentId(comment.getId());

        return commentRequestDto;
    }

    @GetMapping("/delete/{id}")
    public CommentResponseDto deleteComment(@PathVariable("id") Long id) {
        CommentResponseDto commentResponseDto = getCommentDto(id);

        commentService.deleteComment(id);

        return commentResponseDto;
    }
}
