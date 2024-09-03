package com.project.board.repository;

import com.project.board.entity.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    Page<PostResponseDto> getPostDtoList(Pageable pageable);
    PostResponseDto getPostDto(Long id);
}
