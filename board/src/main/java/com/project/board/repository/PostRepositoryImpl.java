package com.project.board.repository;

import com.project.board.entity.CommentResponseDto;
import com.project.board.entity.PostResponseDto;
import com.project.board.entity.QPostResponseDto;
import com.project.board.entity.QCommentResponseDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static com.project.board.entity.QComment.comment;
import static com.project.board.entity.QPost.post;

public class PostRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PostResponseDto> getPostDtoList(Pageable pageable) {
        QueryResults<PostResponseDto> queryResults = queryFactory
                .select(new QPostResponseDto(
                        post.id.as("postId"),
                        post.title,
                        post.content,
                        post.createdDate,
                        post.modifiedDate,
                        Expressions.constant(Collections.emptyList()),
                        post.siteUser.username
                ))
                .from(post)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createdDate.desc())
                .fetchResults();

        List<PostResponseDto> posts  = queryResults.getResults();

        posts.forEach(postResponseDto -> {
            List<CommentResponseDto> comments = queryFactory
                    .select(new QCommentResponseDto(
                            comment.id.as("commentId"),
                            comment.content,
                            comment.createdDate,
                            comment.modifiedDate,
                            comment.siteUser.username,
                            Expressions.constant(postResponseDto.getPostId())
                    ))
                    .from(comment)
                    .where(comment.post.id.eq(postResponseDto.getPostId()))
                    .fetch();

            postResponseDto.setCommentList(comments);
        });

        return new PageImpl<>(posts, pageable, queryResults.getTotal());
    }

    @Override
    public PostResponseDto getPostDto(Long idCond) {
        List<CommentResponseDto> comments = queryFactory
                .select(new QCommentResponseDto(
                        comment.id.as("commentId"),
                        comment.content,
                        comment.createdDate,
                        comment.modifiedDate,
                        comment.siteUser.username,
                        Expressions.constant(idCond)
                ))
                .from(comment)
                .where(comment.post.id.eq(idCond))
                .fetch();

        return queryFactory
                .select(new QPostResponseDto(
                        post.id.as("postId"),
                        post.title,
                        post.content,
                        post.createdDate,
                        post.modifiedDate,
                        Expressions.constant(comments),
                        post.siteUser.username
                ))
                .from(post)
                .where(idEq(idCond))
                .fetchFirst();
    }

    private Predicate idEq(Long idCond) {
        if(idCond == null) return null;

        return post.id.eq(idCond);
    }
}
