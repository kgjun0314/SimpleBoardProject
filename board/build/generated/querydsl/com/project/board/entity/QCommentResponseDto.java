package com.project.board.entity;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.project.board.entity.QCommentResponseDto is a Querydsl Projection type for CommentResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCommentResponseDto extends ConstructorExpression<CommentResponseDto> {

    private static final long serialVersionUID = -220463424L;

    public QCommentResponseDto(com.querydsl.core.types.Expression<Long> commentId, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdDate, com.querydsl.core.types.Expression<java.time.LocalDateTime> modifiedDate, com.querydsl.core.types.Expression<String> siteUserName, com.querydsl.core.types.Expression<Long> postId) {
        super(CommentResponseDto.class, new Class<?>[]{long.class, String.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, String.class, long.class}, commentId, content, createdDate, modifiedDate, siteUserName, postId);
    }

}

