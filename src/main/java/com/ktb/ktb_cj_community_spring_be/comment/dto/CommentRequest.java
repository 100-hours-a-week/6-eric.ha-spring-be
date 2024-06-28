package com.ktb.ktb_cj_community_spring_be.comment.dto;


import com.ktb.ktb_cj_community_spring_be.comment.entity.Comment;
import jakarta.validation.constraints.NotBlank;
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
public class CommentRequest {

      @NotBlank(message = "게시물 Id는 필수입니다.")
      private Long postId;

      @NotBlank(message = "내용은 필수입니다.")
      private String content;


      public Comment toEntity(CommentRequest request) {
            return Comment.builder()
                    .content(request.getContent())
                    .build();
      }
}
