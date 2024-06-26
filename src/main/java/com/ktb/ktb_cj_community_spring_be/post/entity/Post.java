package com.ktb.ktb_cj_community_spring_be.post.entity;

import com.ktb.ktb_cj_community_spring_be.comment.entity.Comment;
import com.ktb.ktb_cj_community_spring_be.global.entity.BaseEntity;
import com.ktb.ktb_cj_community_spring_be.member.entity.Member;
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
public class Post extends BaseEntity {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "member_id")
      private Member member;

      @Column(nullable = false)
      private String title;

      @Column(nullable = false)
      private String content;

      @Column(nullable = false)
      private int hits;

      @Column(nullable = false)
      private int likeCount;

      @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
      private List<Comment> comments = new ArrayList<>();

      @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
      private List<PostLike> postLikes = new ArrayList<>();
}
