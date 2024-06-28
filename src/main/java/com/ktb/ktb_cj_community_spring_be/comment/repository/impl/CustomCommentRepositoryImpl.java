package com.ktb.ktb_cj_community_spring_be.comment.repository.impl;

import static com.ktb.ktb_cj_community_spring_be.comment.entity.QComment.comment;

import com.ktb.ktb_cj_community_spring_be.comment.entity.Comment;
import com.ktb.ktb_cj_community_spring_be.comment.repository.CustomCommentRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
@Slf4j
public class CustomCommentRepositoryImpl implements CustomCommentRepository {

      private final JPAQueryFactory jpa;
      private final EntityManager entityManager;

      @Override
      public Slice<Comment> findCommentsByPost(Long postId, Long commentId, Pageable pageable) {
            List<Comment> commentList = jpa
                    .selectFrom(comment)
                    .where(ltCommentId(commentId),
                            comment.post.id.eq(postId))
                    .orderBy(comment.id.desc())
                    .limit(pageable.getPageSize() + 1)
                    .fetch();

            return checkLastComment(pageable, commentList);
      }

      private BooleanExpression ltCommentId(Long commentId) {
            if (commentId == null || commentId == 0L) {
                  return null;
            }

            return comment.id.lt(commentId);
      }

      private Slice<Comment> checkLastComment(Pageable pageable, List<Comment> results) {

            boolean hasNext = false;

            if (results.size() > pageable.getPageSize()) {
                  hasNext = true;
                  results.remove(pageable.getPageSize());
            }

            return new SliceImpl<>(results, pageable, hasNext);
      }
}
