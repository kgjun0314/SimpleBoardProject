package com.project.board.repository;

import com.project.board.entity.CommentResponseDto;
import com.project.board.entity.PostResponseDto;
import com.project.board.entity.QCommentResponseDto;
import com.project.board.entity.QPostResponseDto;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.project.board.entity.QComment.comment;
import static com.project.board.entity.QPost.post;

public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public CommentResponseDto getCommentDto(Long idCond) {
        return queryFactory
                .select(new QCommentResponseDto(
                        comment.id.as("commentId"),
                        comment.content,
                        comment.createdDate,
                        comment.modifiedDate,
                        comment.siteUser.username,
                        comment.post.id
                ))
                .from(comment)
                .where(idEq(idCond))
                .fetchFirst();
    }

    private Predicate idEq(Long idCond) {
        if(idCond == null) return null;

        return comment.id.eq(idCond);
    }
}
