package com.ktb.ktb_cj_community_spring_be.post.web;


import com.ktb.ktb_cj_community_spring_be.auth.config.LoginUser;
import com.ktb.ktb_cj_community_spring_be.post.dto.PostRequest;
import com.ktb.ktb_cj_community_spring_be.post.dto.PostResponse;
import com.ktb.ktb_cj_community_spring_be.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

      private final PostService postService;

      /**
       * 게시물 생성
       */
      @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
              produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<PostResponse> createPost(
              @Valid @RequestPart("request") PostRequest request,
              @RequestPart(name = "images", required = false) List<MultipartFile> images,
              @LoginUser String email) {

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(postService.createPost(request, images, email));
      }

      /**
       * 전체 게시물 조회
       */
      @GetMapping("/list")
      public ResponseEntity<Page<PostResponse>> getPostList(
              @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable) {

            return ResponseEntity.ok(postService.postList(pageable));
      }

      /**
       * 게시물 상세 조회
       */
      @GetMapping("/detail/{id}")
      public ResponseEntity<PostResponse> getPostDetail(@PathVariable Long id,
              HttpServletRequest request) {
            return ResponseEntity.ok(postService.readPost(id, request));
      }

      /**
       * 게시물 수정
       */
      @PatchMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
              produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<PostResponse> updatePost(@PathVariable Long id,
              @Valid @RequestPart(value = "request") PostRequest request,
              @RequestPart(name = "newImages", required = false) List<MultipartFile> newImages,
              @RequestPart(name = "imageIdsToDelete", required = false) List<Long> imageIdsToDelete,
              @LoginUser String email) {

            return ResponseEntity.ok(postService
                    .updatePost(id, request, newImages, imageIdsToDelete, email));
      }

      /**
       * 게시물 삭제
       */
      @DeleteMapping("/{id}")
      public ResponseEntity<?> deletePost(@PathVariable Long id,
              @LoginUser String email) {
            postService.deletePost(id, email);
            return ResponseEntity.status(HttpStatus.OK).build();
      }


}