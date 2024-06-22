package com.ktb.ktb_cj_community_spring_be.post.dto;


import com.ktb.ktb_cj_community_spring_be.global.util.aws.dto.S3ImageDto;
import com.ktb.ktb_cj_community_spring_be.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

      private LocalDateTime createdAt;
      private LocalDateTime updatedAt;


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
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .build();
      }

}
