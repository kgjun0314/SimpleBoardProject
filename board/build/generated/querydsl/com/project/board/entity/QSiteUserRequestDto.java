package com.project.board.entity;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.project.board.entity.QSiteUserRequestDto is a Querydsl Projection type for SiteUserRequestDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QSiteUserRequestDto extends ConstructorExpression<SiteUserRequestDto> {

    private static final long serialVersionUID = -1061083421L;

    public QSiteUserRequestDto(com.querydsl.core.types.Expression<String> username, com.querydsl.core.types.Expression<String> password, com.querydsl.core.types.Expression<String> email) {
        super(SiteUserRequestDto.class, new Class<?>[]{String.class, String.class, String.class}, username, password, email);
    }

}

