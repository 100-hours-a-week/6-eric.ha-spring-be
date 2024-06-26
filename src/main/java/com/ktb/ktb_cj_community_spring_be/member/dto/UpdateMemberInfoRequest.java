package com.ktb.ktb_cj_community_spring_be.member.dto;

import com.ktb.ktb_cj_community_spring_be.member.entity.Member;
import jakarta.validation.constraints.Email;
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
public class UpdateMemberInfoRequest {

      @Email
      private String email;
      @NotBlank
      private String nickName;

      private String profileImageUrl;

      public void updateMemberInfoToEntity(Member member, String profileImageUrl) {
            member.setNickname(this.nickName);
            member.setProfileImageUrl(profileImageUrl);
      }

}
