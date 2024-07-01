package com.ktb.ktb_cj_community_spring_be.comment.dto;

import lombok.Builder;

@Builder
public record CommentLikeResponse(String email,
                                  Long commentId,
                                  boolean isLike) {

}
