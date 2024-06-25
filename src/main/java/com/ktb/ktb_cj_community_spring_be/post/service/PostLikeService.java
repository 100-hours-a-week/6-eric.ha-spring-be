package com.ktb.ktb_cj_community_spring_be.post.service;

import com.ktb.ktb_cj_community_spring_be.post.dto.PostLikeResponse;

public interface PostLikeService {

      PostLikeResponse likePost(Long postId, String email);

      PostLikeResponse unlikePost(Long postId, String email);

}
