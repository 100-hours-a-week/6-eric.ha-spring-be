package com.ktb.ktb_cj_community_spring_be.comment.repository;

import com.ktb.ktb_cj_community_spring_be.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository {

}
