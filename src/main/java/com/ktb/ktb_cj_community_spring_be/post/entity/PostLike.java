package com.ktb.ktb_cj_community_spring_be.post.entity;

import com.ktb.ktb_cj_community_spring_be.global.entity.BaseEntity;
import com.ktb.ktb_cj_community_spring_be.member.entity.Member;
import jakarta.persistence.*;
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
public class PostLike extends BaseEntity {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "post_id")
      private Post post;

      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "memebr_id")
      private Member member;

}
