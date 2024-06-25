package com.ktb.ktb_cj_community_spring_be.post.service.impl;

import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.ALREADY_LIKED;
import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.POST_NOT_FOUND;
import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.USER_NOT_FOUND;

import com.ktb.ktb_cj_community_spring_be.global.exception.GlobalException;
import com.ktb.ktb_cj_community_spring_be.member.entity.Member;
import com.ktb.ktb_cj_community_spring_be.member.repository.MemberRepository;
import com.ktb.ktb_cj_community_spring_be.post.dto.PostLikeResponse;
import com.ktb.ktb_cj_community_spring_be.post.entity.Post;
import com.ktb.ktb_cj_community_spring_be.post.entity.PostLike;
import com.ktb.ktb_cj_community_spring_be.post.repository.PostLikeRepository;
import com.ktb.ktb_cj_community_spring_be.post.repository.PostRepository;
import com.ktb.ktb_cj_community_spring_be.post.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

      private final PostRepository postRepository;
      private final PostLikeRepository postLikeRepository;
      private final MemberRepository memberRepository;

      @Override
      public PostLikeResponse likePost(Long postId, String email) {
            if (postLikeRepository.existsPostLikeByPostIdAndMember_Email(postId, email)) {
                  throw new GlobalException(ALREADY_LIKED);
            }

            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new GlobalException(POST_NOT_FOUND));

            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new GlobalException(USER_NOT_FOUND));

            PostLike postLike = PostLike.builder().build();

            postLike.addMember(member);
            postLike.addPost(post);
            post.updateLikeCount();

            postLikeRepository.save(postLike);
            return PostLikeResponse.builder()
                    .postId(postId)
                    .memberId(member.getId())
                    .memberEmail(member.getEmail())
                    .isLike(true)
                    .build();
      }

      @Override
      public PostLikeResponse unlikePost(Long postId, String email) {

            return postLikeRepository.findByPostIdAndMember_Email(postId, email)
                    .map(postLike -> {
                          postLikeRepository.delete(postLike);
                          postRepository.findById(postId).ifPresent(e -> {
                                e.removePostLike(postLike);
                                e.updateLikeCount();
                          });

                          return PostLikeResponse.builder()
                                  .postId(postId)
                                  .memberId(postLike.getMember().getId())
                                  .memberEmail(postLike.getMember().getEmail())
                                  .isLike(false)
                                  .build();
                    }).orElseThrow(() -> new GlobalException(POST_NOT_FOUND));
      }
}
