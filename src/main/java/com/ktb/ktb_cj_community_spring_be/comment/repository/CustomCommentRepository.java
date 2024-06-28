package com.ktb.ktb_cj_community_spring_be.comment.repository;

import com.ktb.ktb_cj_community_spring_be.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomCommentRepository {

      Slice<Comment> findCommentsByPost(Long postId, Long commentId, Pageable pageable);

}
