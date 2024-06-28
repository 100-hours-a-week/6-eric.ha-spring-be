package com.ktb.ktb_cj_community_spring_be.post.repository;


import com.ktb.ktb_cj_community_spring_be.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {

}
