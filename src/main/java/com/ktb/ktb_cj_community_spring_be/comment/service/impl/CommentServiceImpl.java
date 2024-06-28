package com.ktb.ktb_cj_community_spring_be.comment.service.impl;

import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.COMMENT_NOT_FOUND;
import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.POST_NOT_FOUND;
import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.USER_NOT_FOUND;
import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.WRITE_NOT_YOURSELF;

import com.ktb.ktb_cj_community_spring_be.comment.dto.CommentRequest;
import com.ktb.ktb_cj_community_spring_be.comment.dto.CommentResponse;
import com.ktb.ktb_cj_community_spring_be.comment.entity.Comment;
import com.ktb.ktb_cj_community_spring_be.comment.repository.CommentRepository;
import com.ktb.ktb_cj_community_spring_be.comment.service.CommentService;
import com.ktb.ktb_cj_community_spring_be.global.exception.GlobalException;
import com.ktb.ktb_cj_community_spring_be.member.entity.Member;
import com.ktb.ktb_cj_community_spring_be.member.repository.MemberRepository;
import com.ktb.ktb_cj_community_spring_be.post.entity.Post;
import com.ktb.ktb_cj_community_spring_be.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

      private final CommentRepository commentRepository;
      private final MemberRepository memberRepository;
      private final PostRepository postRepository;

      @Override
      public CommentResponse createComment(CommentRequest request, String email) {
            Member member = getMember(email);
            Post post = getPost(request.getPostId());

            Comment comment = request.toEntity(request);

            comment.addPostAndMember(post, member);

            return CommentResponse.fromEntity(commentRepository.save(comment));
      }


      @Override
      @Transactional
      public CommentResponse updateComment(CommentRequest request, Long commentId, String email) {
            postRepository.findById(request.getPostId())
                    .orElseThrow(() -> new GlobalException(POST_NOT_FOUND));

            Comment comment = getComment(commentId);

            validationComment(comment, email);

            comment.changeContent(request.getContent());

            return CommentResponse.fromEntity(comment);
      }


      @Override
      @Transactional
      public void deleteComment(Long commentId, String email) {
            Comment comment = getComment(commentId);
            validationComment(comment, email);
            commentRepository.delete(comment);
      }

      @Override
      public Slice<CommentResponse> getComments(Long postId, Long commentId, Pageable pageable) {
            return commentRepository.findCommentsByPost(postId, commentId, pageable)
                    .map(CommentResponse::fromEntity);
      }

      private Comment getComment(Long commentId) {
            return commentRepository.findById(commentId)
                    .orElseThrow(() -> new GlobalException(COMMENT_NOT_FOUND));
      }

      private Post getPost(Long postId) {
            return postRepository.findById(postId)
                    .orElseThrow(() -> new GlobalException(POST_NOT_FOUND));
      }

      private Member getMember(String email) {
            return memberRepository.findByEmail(email)
                    .orElseThrow(() -> new GlobalException(USER_NOT_FOUND));
      }

      private void validationComment(Comment comment, String email) {
            if (!comment.getMember().getEmail().equals(email)) {
                  throw new GlobalException(WRITE_NOT_YOURSELF);
            }
      }

}
