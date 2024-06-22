package com.ktb.ktb_cj_community_spring_be.post.dto;


import com.ktb.ktb_cj_community_spring_be.global.util.aws.entity.PostImage;
import com.ktb.ktb_cj_community_spring_be.post.entity.Post;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostRequest {


      @NotNull(message = "제목을 입력해주세요.")
      private String title;
      @NotNull(message = "내용을 입력해주세요.")
      private String content;

      private List<PostImage> images;


      public Post toEntity() {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .build();
      }

      public void updatePostEntity(Post post) {
            post.setTitle(title);
            post.setContent(content);
      }


}
