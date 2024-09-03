package com.project.board.entity;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.project.board.entity.QCommentRequestDto is a Querydsl Projection type for CommentRequestDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCommentRequestDto extends ConstructorExpression<CommentRequestDto> {

    private static final long serialVersionUID = -1553645650L;

    public QCommentRequestDto(com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<String> username) {
        super(CommentRequestDto.class, new Class<?>[]{String.class, String.class}, content, username);
    }

}

