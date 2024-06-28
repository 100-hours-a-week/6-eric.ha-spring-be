package com.ktb.ktb_cj_community_spring_be.comment.web;

import com.ktb.ktb_cj_community_spring_be.auth.config.LoginUser;
import com.ktb.ktb_cj_community_spring_be.comment.dto.CommentRequest;
import com.ktb.ktb_cj_community_spring_be.comment.dto.CommentResponse;
import com.ktb.ktb_cj_community_spring_be.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
public class CommentController {

      private final CommentService commentService;

      /**
       * 해당 게시물 댓글 목록 조회
       */
      @GetMapping("/{postId}")
      public ResponseEntity<?> getCommentList(@PathVariable("postId") Long postId,
              @RequestParam(value = "commentId", required = false) Long commentId,
              @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {

            return ResponseEntity.ok(commentService.getComments(postId, commentId, pageable));
      }

      /**
       * 댓글 생성
       */
      @PostMapping
      public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest request,
              @LoginUser String email) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(commentService.createComment(request, email));
      }

      /**
       * 댓글 수정
       */
      @PatchMapping
      public ResponseEntity<CommentResponse> updateComment(@RequestBody CommentRequest request,
              @Valid @RequestParam("commentId") Long commentId, @LoginUser String email) {
            return ResponseEntity.ok(commentService.updateComment(request, commentId, email));
      }

      /**
       * 댓글 삭제
       */
      @DeleteMapping
      public ResponseEntity<?> deleteComment(@RequestParam("id") Long commentId,
              @LoginUser String email) {
            commentService.deleteComment(commentId, email);
            return ResponseEntity.noContent().build();
      }


}
