package com.ktb.ktb_cj_community_spring_be.comment.web;

import com.ktb.ktb_cj_community_spring_be.auth.config.LoginUser;
import com.ktb.ktb_cj_community_spring_be.comment.dto.CommentLikeResponse;
import com.ktb.ktb_cj_community_spring_be.comment.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentLikeController {

      private final CommentLikeService commentLikeService;

      @PostMapping("/like")
      public ResponseEntity<CommentLikeResponse> likeComment(
              @RequestParam(name = "id") Long commentId
              , @LoginUser String email) {
            return ResponseEntity.ok(commentLikeService.likeComment(commentId, email));
      }


      @PostMapping("/unlike")
      public ResponseEntity<CommentLikeResponse> unlikeComment(
              @RequestParam(name = "id") Long commentId,
              @LoginUser String email) {
            return ResponseEntity.ok(commentLikeService.unlikeComment(commentId, email));
      }


}
