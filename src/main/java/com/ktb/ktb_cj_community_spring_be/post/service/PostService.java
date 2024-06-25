package com.ktb.ktb_cj_community_spring_be.post.service;

import com.ktb.ktb_cj_community_spring_be.post.dto.PostRequest;
import com.ktb.ktb_cj_community_spring_be.post.dto.PostResponse;
import com.ktb.ktb_cj_community_spring_be.post.entity.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {


      PostResponse createPost(PostRequest request, List<MultipartFile> images, String email);

      PostResponse readPost(Long postId);

      PostResponse updatePost(Long id, PostRequest request, List<MultipartFile> newImages,
              List<Long> imageIdsToDelete, String email);

      void deletePost(Long id, String email);

      Post uploadS3Image(PostRequest request, List<MultipartFile> multipartFiles);

      // 게시물 전체 리스트 - 페이징 처리
      Page<PostResponse> postList(Pageable pageable);

      // 게시물 전체 리스트 - 무한 스크롤
      Slice<PostResponse> postListScroll(Pageable pageable);
}
