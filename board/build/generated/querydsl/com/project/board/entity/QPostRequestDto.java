package com.project.board.entity;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.project.board.entity.QPostRequestDto is a Querydsl Projection type for PostRequestDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPostRequestDto extends ConstructorExpression<PostRequestDto> {

    private static final long serialVersionUID = 392442577L;

    public QPostRequestDto(com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<String> username) {
        super(PostRequestDto.class, new Class<?>[]{String.class, String.class, String.class}, title, content, username);
    }

}

