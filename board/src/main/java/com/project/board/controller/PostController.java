package com.project.board.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.board.entity.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/post")
@Controller
@RequiredArgsConstructor
public class PostController {
    private final RestTemplate restTemplate;

    @GetMapping("/list")
    public String listPost(Model model, @RequestParam(value="page", defaultValue = "0") int page) {
        String url = "http://localhost:8080/api/post/list?page=" + page;

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> response = responseEntity.getBody();
        List<PostResponseDto> content = (List<PostResponseDto>) response.get("content");
        int totalPages = (Integer) response.get("totalPages");
        long totalElements = ((Number) response.get("totalElements")).longValue();
        int number = (Integer) response.get("number");
        int size = (Integer) response.get("size");

        Page<PostResponseDto> postPage = new PageImpl<>(content, PageRequest.of(number, size), totalElements);

        model.addAttribute("postPage", postPage);
        return "post_list";
    }

    @GetMapping("/detail/{id}")
    public String detailPost(Model model, @PathVariable("id") Long id, CommentForm commentForm) {
        String url = "http://localhost:8080/api/post/detail/" + id;
        PostResponseDto postResponseDto = restTemplate.getForObject(url, PostResponseDto.class);
        model.addAttribute("post", postResponseDto);
        return "post_detail";
    }

    @GetMapping("/create")
    public String createPost(PostForm postForm) {
        return "post_form";
    }

    @PostMapping("/create")
    public String createPost(Model model, @Valid PostForm postForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "post_form";
        }

        String url = "http://localhost:8080/api/post/create";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", postForm.getTitle());
        requestBody.put("content", postForm.getContent());
        requestBody.put("username", principal.getName());

        // 글 작성 API 호출
        try {
            restTemplate.postForObject(url, requestBody, String.class);
        } catch (Exception e) {
            // 에러 발생 시 처리할 로직 (예: 에러 메시지 추가)
            model.addAttribute("errorMessage", "글 생성 중 오류가 발생했습니다.");
            return "error";
        }

        return "redirect:/post/list";
    }

    @GetMapping("/modify/{id}")
    public String modifyPost(PostForm postForm, @PathVariable("id") Long id, Principal principal) {
        String url = "http://localhost:8080/api/post/detail/" + id;
        PostResponseDto postResponseDto = restTemplate.getForObject(url, PostResponseDto.class);
        if(!postResponseDto.getSiteUserName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        postForm.setTitle(postResponseDto.getTitle());
        postForm.setContent(postResponseDto.getContent());
        return "post_form";
    }

    @PostMapping("/modify/{id}")
    public String modifyPost(Model model, @Valid PostForm postForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "post_form";
        }

        String url = "http://localhost:8080/api/post/detail/" + id;
        PostResponseDto postResponseDto = restTemplate.getForObject(url, PostResponseDto.class);
        if(!postResponseDto.getSiteUserName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        url = "http://localhost:8080/api/post/modify/" + id;
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", postForm.getTitle());
        requestBody.put("content", postForm.getContent());
        requestBody.put("username", principal.getName());

        // 글 작성 API 호출
        try {
            restTemplate.postForObject(url, requestBody, String.class);
        } catch (Exception e) {
            // 에러 발생 시 처리할 로직 (예: 에러 메시지 추가)
            model.addAttribute("errorMessage", "글 수정 중 오류가 발생했습니다.");
            return "error";
        }

        return String.format("redirect:/post/detail/%d", id);
    }

    @GetMapping("/delete/{id}")
    public String deletePost(Model model, @PathVariable("id") Long id, Principal principal) {
        String url = "http://localhost:8080/api/post/detail/" + id;
        PostResponseDto postResponseDto = restTemplate.getForObject(url, PostResponseDto.class);
        if(!postResponseDto.getSiteUserName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        url = "http://localhost:8080/api/post/delete/" + id;

        // 글 작성 API 호출
        try {
            restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            // 에러 발생 시 처리할 로직 (예: 에러 메시지 추가)
            model.addAttribute("errorMessage", "글 삭제 중 오류가 발생했습니다.");
            return "error";
        }

        return "redirect:/";
    }
}
