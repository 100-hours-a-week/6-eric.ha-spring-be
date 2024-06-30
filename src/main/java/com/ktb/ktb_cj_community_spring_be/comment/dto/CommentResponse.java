package com.ktb.ktb_cj_community_spring_be.comment.dto;

import com.ktb.ktb_cj_community_spring_be.comment.entity.Comment;
import java.time.LocalDateTime;
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
public class CommentResponse {

      private Long memberId;
      private Long postId;
      private Long commentId;

      private String memberEmail;
      private String memberNickname;
      private String content;


      private LocalDateTime createdAt;
      private LocalDateTime updatedAt;

      public static CommentResponse fromEntity(Comment comment) {
            return CommentResponse.builder()
                    .memberId(comment.getMember().getId())
                    .postId(comment.getPost().getId())
                    .commentId(comment.getId())
                    .memberEmail(comment.getMember().getEmail())
                    .memberNickname(comment.getMember().getNickname())
                    .content(comment.getContent())
                    .createdAt(comment.getCreatedAt())
                    .updatedAt(comment.getUpdatedAt())
                    .build();
      }

}
