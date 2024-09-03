package com.project.board.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class SiteUserRequestDto {
    private String username;

    private String password;

    private String email;

    public SiteUserRequestDto() {}

    @QueryProjection
    public SiteUserRequestDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
