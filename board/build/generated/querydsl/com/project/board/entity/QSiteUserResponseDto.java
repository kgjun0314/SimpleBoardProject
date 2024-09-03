package com.project.board.entity;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.project.board.entity.QSiteUserResponseDto is a Querydsl Projection type for SiteUserResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QSiteUserResponseDto extends ConstructorExpression<SiteUserResponseDto> {

    private static final long serialVersionUID = -2130903509L;

    public QSiteUserResponseDto(com.querydsl.core.types.Expression<String> username, com.querydsl.core.types.Expression<String> password, com.querydsl.core.types.Expression<String> email) {
        super(SiteUserResponseDto.class, new Class<?>[]{String.class, String.class, String.class}, username, password, email);
    }

}

