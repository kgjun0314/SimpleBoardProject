package com.project.board.service;

import com.project.board.entity.Post;
import com.project.board.entity.PostRequestDto;
import com.project.board.entity.PostResponseDto;
import com.project.board.entity.SiteUser;
import com.project.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Page<PostResponseDto> getPostDtoList(Pageable pageable) {
        return postRepository.getPostDtoList(pageable);
    }

    public PostResponseDto getPostDto(Long id) {
        return postRepository.getPostDto(id);
    }

    public Post getPostEntity(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Transactional
    public void createPost(PostRequestDto postRequestDto, SiteUser siteUser) {

        Post post = new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setSiteUser(siteUser);

        postRepository.save(post);
    }

    @Transactional
    public void modifyPost(PostRequestDto postRequestDto, Long id) {
        Post post = getPostEntity(id);
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setModifiedDate(LocalDateTime.now());
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = getPostEntity(id);
        postRepository.delete(post);
    }
}
