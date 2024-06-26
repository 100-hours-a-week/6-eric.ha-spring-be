package com.ktb.ktb_cj_community_spring_be.member.dto;

import com.ktb.ktb_cj_community_spring_be.member.entity.Member;
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
public class MemberResponse {

      private Long memberId;
      private String memberEmail;
      private String memberNickname;

      private String memberProfileImageUrl;

      public static MemberResponse fromEntity(Member member) {
            return MemberResponse.builder()
                    .memberId(member.getId())
                    .memberEmail(member.getEmail())
                    .memberNickname(member.getNickname())
                    .memberProfileImageUrl(member.getProfileImageUrl())
                    .build();
      }
}
