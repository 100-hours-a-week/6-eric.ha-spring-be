package com.ktb.ktb_cj_community_spring_be.post.dto;


import com.ktb.ktb_cj_community_spring_be.comment.dto.CommentResponse;
import com.ktb.ktb_cj_community_spring_be.global.util.aws.dto.S3ImageDto;
import com.ktb.ktb_cj_community_spring_be.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Slice;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {

      private Long id;
      private Long memberId;
      private String memberEmail;

      private String title;
      private String content;
      private List<S3ImageDto> images;

      private int hits;
      private int likeCount;
      private int commentCount;

      private LocalDateTime createdAt;
      private LocalDateTime updatedAt;

      private Slice<CommentResponse> comments;


      //게시물 상세 조회
      public static PostResponse fromEntity(Post post) {
            List<S3ImageDto> imageList = post.getImages().stream()
                    .map(S3ImageDto::fromEntity).toList();

            return PostResponse.builder()
                    .id(post.getId())
                    .memberId(post.getMember().getId())
                    .memberEmail(post.getMember().getEmail())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .images(imageList)
                    .hits(post.getHits())
                    .likeCount(post.getLikeCount())
                    .commentCount(post.getComments().size())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .build();
      }

      public static PostResponse listFromEntity(Post post) {
            return PostResponse.builder()
                    .id(post.getId())
                    .memberId(post.getMember().getId())
                    .memberEmail(post.getMember().getEmail())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .build();
      }

}
