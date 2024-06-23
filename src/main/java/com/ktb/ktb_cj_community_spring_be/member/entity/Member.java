package com.ktb.ktb_cj_community_spring_be.member.entity;

import com.ktb.ktb_cj_community_spring_be.comment.entity.Comment;
import com.ktb.ktb_cj_community_spring_be.comment.entity.CommentLike;
import com.ktb.ktb_cj_community_spring_be.global.entity.BaseEntity;
import com.ktb.ktb_cj_community_spring_be.member.entity.type.MemberRoleType;
import com.ktb.ktb_cj_community_spring_be.post.entity.Post;
import com.ktb.ktb_cj_community_spring_be.post.entity.PostLike;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @Column(nullable = false, unique = true)
      private String email;

      @Column(nullable = false)
      private String password;

      @Column(nullable = false, unique = true, length = 50)
      private String nickname;

      @Column(nullable = false, length = 50)
      private MemberRoleType roleType;

      @Column(nullable = false)
      private String profileImageUrl;

//    @Builder.Default
//    private boolean emailAuth = false;

      @Builder.Default
      @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
      private List<Post> posts = new ArrayList<>();

      @Builder.Default
      @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
      private List<Comment> comments = new ArrayList<>();

      @Builder.Default
      @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
      private List<PostLike> postLikes = new ArrayList<>();

      @Builder.Default
      @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
      private List<CommentLike> commentLikes = new ArrayList<>();

      public void addPost(Post post) {
            this.posts.add(post);
            post.setMember(this);
      }

      public void removePost(Post post) {
            this.posts.remove(post);
            post.setMember(null);
      }
}
