package com.ktb.ktb_cj_community_spring_be.post.dto;

import lombok.Builder;

@Builder
public record PostLikeResponse(
        Long memberId,
        String memberEmail,
        Long postId,
        boolean isLike
) {

}
