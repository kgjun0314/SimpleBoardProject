package com.project.board.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class SiteUserResponseDto {
    private String username;
    private String password;
    private String email;

    public SiteUserResponseDto() {}

    @QueryProjection
    public SiteUserResponseDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
