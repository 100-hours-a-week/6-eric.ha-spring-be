package com.ktb.ktb_cj_community_spring_be.comment.service;

import com.ktb.ktb_cj_community_spring_be.comment.dto.CommentLikeResponse;

public interface CommentLikeService {

      CommentLikeResponse likeComment(Long commentId, String email);

      CommentLikeResponse unlikeComment(Long commentId, String email);

}
