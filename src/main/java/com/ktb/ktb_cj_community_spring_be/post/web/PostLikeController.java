package com.ktb.ktb_cj_community_spring_be.post.web;

import com.ktb.ktb_cj_community_spring_be.auth.config.LoginUser;
import com.ktb.ktb_cj_community_spring_be.post.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostLikeController {

      private final PostLikeService postLikeService;

      @PostMapping("/like")
      public ResponseEntity<?> likePost(@RequestParam("id") Long postId,
              @LoginUser String email) {
            return ResponseEntity.ok(postLikeService.likePost(postId, email));
      }

      @PostMapping("/unlike")
      public ResponseEntity<?> unlikePost(@RequestParam("id") Long postId,
              @LoginUser String email) {
            return ResponseEntity.ok(postLikeService.unlikePost(postId, email));
      }
}
