package com.project.board.entity;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.project.board.entity.QPostResponseDto is a Querydsl Projection type for PostResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPostResponseDto extends ConstructorExpression<PostResponseDto> {

    private static final long serialVersionUID = -21270531L;

    public QPostResponseDto(com.querydsl.core.types.Expression<Long> postId, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdDate, com.querydsl.core.types.Expression<java.time.LocalDateTime> modifiedDate, com.querydsl.core.types.Expression<? extends java.util.List<CommentResponseDto>> commentList, com.querydsl.core.types.Expression<String> siteUserName) {
        super(PostResponseDto.class, new Class<?>[]{long.class, String.class, String.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, java.util.List.class, String.class}, postId, title, content, createdDate, modifiedDate, commentList, siteUserName);
    }

}

