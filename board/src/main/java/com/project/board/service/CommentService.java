package com.project.board.service;

import com.project.board.entity.*;
import com.project.board.repository.CommentRepository;
import com.project.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentResponseDto getCommentDto(Long id) {
        return commentRepository.getCommentDto(id);
    }
    public Comment getCommentEntity(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Transactional
    public Comment createComment(Post post, SiteUser siteUser, CommentRequestDto commentRequestDto) {

        Comment comment = new Comment();
        comment.setContent(commentRequestDto.getContent());
        comment.setPost(post);
        comment.setSiteUser(siteUser);

        this.commentRepository.save(comment);

        return comment;
    }

    @Transactional
    public Comment modifyComment(CommentRequestDto commentRequestDto, Long id) {
        Comment comment = getCommentEntity(id);
        comment.setContent(commentRequestDto.getContent());
        comment.setModifiedDate(LocalDateTime.now());
        this.commentRepository.save(comment);

        return comment;
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = getCommentEntity(id);
        commentRepository.delete(comment);
    }
}
