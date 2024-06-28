package com.ktb.ktb_cj_community_spring_be.post.repository;

import com.ktb.ktb_cj_community_spring_be.post.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomPostRepository {

      Slice<Post> searchByTitle(Long postId, String title, Pageable pageable);

      @Transactional
      void updateViews(Long postId, int hits);

      Slice<Post> findAllPosts(Long postId, Pageable pageable);
}
