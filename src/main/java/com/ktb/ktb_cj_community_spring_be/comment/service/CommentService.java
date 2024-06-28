package com.ktb.ktb_cj_community_spring_be.comment.service;

import com.ktb.ktb_cj_community_spring_be.comment.dto.CommentRequest;
import com.ktb.ktb_cj_community_spring_be.comment.dto.CommentResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentService {

      CommentResponse createComment(CommentRequest request, String email);

      CommentResponse updateComment(CommentRequest request, Long commentId, String email);

      void deleteComment(Long commentId, String email);

      Slice<CommentResponse> getComments(Long postId, Long commentId, Pageable pageable);
}
