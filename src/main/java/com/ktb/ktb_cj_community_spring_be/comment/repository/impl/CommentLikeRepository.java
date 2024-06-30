package com.ktb.ktb_cj_community_spring_be.comment.repository.impl;

import com.ktb.ktb_cj_community_spring_be.comment.entity.CommentLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

      /**
       * 특정 회원이 특정 댓글에 좋아요를 눌렀는지 확인
       */
      boolean existsCommentLikeByCommentIdAndMember_Email(Long commentId, String email);

      /**
       * 특정 회원이 특정 댓글에 눌렀던 좋아요를 반환
       */
      Optional<CommentLike> findCommentLikeByCommentIdAndMember_Email(Long commentId, String email);

}