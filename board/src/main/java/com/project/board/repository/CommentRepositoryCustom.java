package com.project.board.repository;

import com.project.board.entity.CommentResponseDto;

public interface CommentRepositoryCustom {
    CommentResponseDto getCommentDto(Long id);
}
