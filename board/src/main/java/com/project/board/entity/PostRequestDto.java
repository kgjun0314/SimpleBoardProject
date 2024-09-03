package com.project.board.entity;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequestDto {
    private String title;

    private String content;

    private String username;

    public PostRequestDto() {}

    @QueryProjection
    public PostRequestDto(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }
}
