package com.ktb.ktb_cj_community_spring_be.comment.service.impl;

import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.ALREADY_LIKED;
import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.ALREADY_UNLIKED;
import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.USER_NOT_FOUND;

import com.ktb.ktb_cj_community_spring_be.comment.dto.CommentLikeResponse;
import com.ktb.ktb_cj_community_spring_be.comment.entity.Comment;
import com.ktb.ktb_cj_community_spring_be.comment.entity.CommentLike;
import com.ktb.ktb_cj_community_spring_be.comment.repository.CommentRepository;
import com.ktb.ktb_cj_community_spring_be.comment.repository.impl.CommentLikeRepository;
import com.ktb.ktb_cj_community_spring_be.comment.service.CommentLikeService;
import com.ktb.ktb_cj_community_spring_be.global.exception.GlobalException;
import com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode;
import com.ktb.ktb_cj_community_spring_be.member.entity.Member;
import com.ktb.ktb_cj_community_spring_be.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {

      private final MemberRepository memberRepository;
      private final CommentRepository commentRepository;
      private final CommentLikeRepository commentLikeRepository;

      @Override
      @Transactional
      public CommentLikeResponse likeComment(Long commentId, String email) {
            if (commentLikeRepository.existsCommentLikeByCommentIdAndMember_Email(commentId,
                    email)) {
                  throw new GlobalException(ALREADY_LIKED);
            }
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));

            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new GlobalException(USER_NOT_FOUND));

            CommentLike commentLike = CommentLike.builder().build();

            commentLike.addComment(comment);
            commentLike.addMember(member);
            comment.updateLikeCount();

            commentLikeRepository.save(commentLike);

            return CommentLikeResponse.builder()
                    .commentId(commentId)
                    .email(email)
                    .isLike(true)
                    .build();
      }

      @Override
      @Transactional
      public CommentLikeResponse unlikeComment(Long commentId, String email) {
            return commentLikeRepository.findCommentLikeByCommentIdAndMember_Email(commentId, email)
                    .map(like -> {
                          commentLikeRepository.delete(like);
                          commentRepository.findById(commentId).ifPresent(e -> {
                                e.removeCommentLike(like);
                                e.updateLikeCount();
                          });
                          return CommentLikeResponse.builder()
                                  .commentId(commentId)
                                  .email(email)
                                  .isLike(false)
                                  .build();
                    }).orElseThrow(() -> new GlobalException(ALREADY_UNLIKED));
      }
}
