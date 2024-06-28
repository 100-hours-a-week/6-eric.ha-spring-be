package com.ktb.ktb_cj_community_spring_be.comment.entity;

import com.ktb.ktb_cj_community_spring_be.global.entity.BaseEntity;
import com.ktb.ktb_cj_community_spring_be.member.entity.Member;
import com.ktb.ktb_cj_community_spring_be.post.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Comment extends BaseEntity {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "post_id")
      private Post post;

      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "member_id")
      private Member member;

      @Column(nullable = false)
      private String content;

      @Column(nullable = false)
      private int likeCount;

      @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, orphanRemoval = true)
      private List<CommentLike> commentLikes;

      public void addPostAndMember(Post post, Member member) {
            this.post = post;
            this.member = member;

            post.addComment(this);
      }

      public void changeContent(String content) {
            this.content = content;
      }

      public void addCommentLike(CommentLike commentLike) {
            this.commentLikes.add(commentLike);
      }

      public void updateLikeCount() {
            this.likeCount = this.commentLikes.size();
      }

      public void removeCommentLike(CommentLike commentLike) {
            this.commentLikes.remove(commentLike);
      }

}
