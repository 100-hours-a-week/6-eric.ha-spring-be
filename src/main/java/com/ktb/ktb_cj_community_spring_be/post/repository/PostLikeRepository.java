package com.ktb.ktb_cj_community_spring_be.post.repository;

import com.ktb.ktb_cj_community_spring_be.post.entity.PostLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

      boolean existsPostLikeByPostIdAndMember_Email(Long postId, String email);

      Optional<PostLike> findByPostIdAndMember_Email(Long postId, String email);

}
