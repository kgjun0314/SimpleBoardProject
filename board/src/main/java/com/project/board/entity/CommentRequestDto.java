package com.project.board.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class CommentRequestDto {
    private Long commentId;
    private String content;
    private String username;

    public CommentRequestDto() {}

    @QueryProjection
    public CommentRequestDto(String content, String username) {
        this.content = content;
        this.username = username;
    }
}
