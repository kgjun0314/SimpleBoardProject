package com.project.board.controller;

import com.project.board.entity.*;
import com.project.board.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {
    private final RestTemplate restTemplate;

    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Long id, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            String url = "http://localhost:8080/api/post/detail/" + id;
            PostResponseDto postResponseDto = restTemplate.getForObject(url, PostResponseDto.class);
            model.addAttribute("post", postResponseDto);
            return "post_detail";
        }
        String url = "http://localhost:8080/api/comment/create/" + id;

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("content", commentForm.getContent());
        requestBody.put("username", principal.getName());

        // 댓글 생성 API 호출
        try {
            ResponseEntity<CommentRequestDto> responseEntity = restTemplate.postForEntity(url, requestBody, CommentRequestDto.class);
            Long commentId = responseEntity.getBody().getCommentId();

            // 댓글 ID가 null인 경우 에러 처리
            if (commentId == null) {
                model.addAttribute("errorMessage", "댓글 생성 중 오류가 발생했습니다.");
                return "error";
            }

            return String.format("redirect:/post/detail/%d#comment_%d", id, commentId);
        } catch (Exception e) {
            // 에러 발생 시 처리할 로직 (예: 에러 메시지 추가)
            model.addAttribute("errorMessage", "댓글 생성 중 오류가 발생했습니다.");
            return "error";
        }
    }

    @GetMapping("/modify/{id}")
    public String modifyComment(CommentForm commentForm, @PathVariable("id") Long id, Principal principal) {
        String url = "http://localhost:8080/api/comment/detail/" + id;
        CommentResponseDto commentResponseDto = restTemplate.getForObject(url, CommentResponseDto.class);
        if(!commentResponseDto.getSiteUserName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        commentForm.setContent(commentResponseDto.getContent());
        return "comment_form";
    }

    @PostMapping("/modify/{id}")
    public String modifyComment(Model model, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "comment_form";
        }

        String url = "http://localhost:8080/api/comment/detail/" + id;
        CommentResponseDto commentResponseDto = restTemplate.getForObject(url, CommentResponseDto.class);
        if(!commentResponseDto.getSiteUserName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        url = "http://localhost:8080/api/comment/modify/" + id;
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("content", commentForm.getContent());
        requestBody.put("username", principal.getName());

        // 댓글 수정 API 호출
        try {
            ResponseEntity<CommentRequestDto> responseEntity = restTemplate.postForEntity(url, requestBody, CommentRequestDto.class);
            Long commentId = responseEntity.getBody().getCommentId();

            // 댓글 ID가 null인 경우 에러 처리
            if (commentId == null) {
                model.addAttribute("errorMessage", "댓글 수정 중 오류가 발생했습니다.");
                return "error";
            }

            return String.format("redirect:/post/detail/%d#comment_%d", commentResponseDto.getPostId(), commentId);
        } catch (Exception e) {
            // 에러 발생 시 처리할 로직 (예: 에러 메시지 추가)
            model.addAttribute("errorMessage", "댓글 수정 중 오류가 발생했습니다.");
            return "error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePost(Model model, @PathVariable("id") Long id, Principal principal) {
        String url = "http://localhost:8080/api/comment/detail/" + id;
        CommentResponseDto commentResponseDto = restTemplate.getForObject(url, CommentResponseDto.class);
        if(!commentResponseDto.getSiteUserName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        url = "http://localhost:8080/api/comment/delete/" + id;

        // 글 작성 API 호출
        try {
            restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            // 에러 발생 시 처리할 로직 (예: 에러 메시지 추가)
            model.addAttribute("errorMessage", "댓글 삭제 중 오류가 발생했습니다.");
            return "error";
        }

        return String.format("redirect:/post/detail/%d", commentResponseDto.getPostId());
    }
}
