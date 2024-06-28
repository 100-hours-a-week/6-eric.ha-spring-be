package com.ktb.ktb_cj_community_spring_be.post.repository.impl;

import static com.ktb.ktb_cj_community_spring_be.post.entity.QPost.post;

import com.ktb.ktb_cj_community_spring_be.post.entity.Post;
import com.ktb.ktb_cj_community_spring_be.post.repository.CustomPostRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class CustomPostRepositoryImpl implements CustomPostRepository {

      private final JPAQueryFactory jpa;
      private final EntityManager entityManager;

      @Override
      public Slice<Post> searchByTitle(Long postId, String title, Pageable pageable) {
            List<Post> postList = jpa
                    .selectFrom(post)
                    .where(
                            ltPostId(postId)
                            , post.title.contains(title)
                    )
                    .orderBy(
                            post.id.desc()
                            , post.hits.desc()
                            , post.likeCount.desc()
                    )
                    .limit(pageable.getPageSize() + 1)
                    .fetch();

            return checkLastPage(pageable, postList);
      }


      @Override
      public void updateViews(Long postId, int hits) {
            jpa.update(post)
                    .set(post.hits, post.hits.add(hits))
                    .where(post.id.eq(postId))
                    .execute();
            entityManager.flush();
            entityManager.clear();
      }

      private BooleanExpression ltPostId(Long postId) {
            if (postId == null || postId == 0L) {
                  return null;
            }
            return post.id.lt(postId);
      }

      /**
       * 무한 스크롤을 위한 게시물 조회
       *
       * @param postId   마지막 게시물의 id
       * @param pageable 페이지 정보
       * @return 조회된 게시물 리스트
       */
      public Slice<Post> findAllPosts(Long postId, Pageable pageable) {
            List<Post> postList = jpa
                    .selectFrom(post)
                    .where(ltPostId(postId))
                    .orderBy(
                            post.id.desc()
                            , post.hits.desc()
                            , post.likeCount.desc()
                    )
                    .limit(pageable.getPageSize() + 1)
                    .fetch();

            return checkLastPage(pageable, postList);
      }

      private Slice<Post> checkLastPage(Pageable pageable, List<Post> results) {

            boolean hasNext = false;

            if (results.size() > pageable.getPageSize()) {
                  hasNext = true;
                  results.remove(pageable.getPageSize());
            }
            return new SliceImpl<>(results, pageable, hasNext);
      }
}
