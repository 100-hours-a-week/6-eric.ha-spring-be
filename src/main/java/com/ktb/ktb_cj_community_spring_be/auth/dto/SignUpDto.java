package com.ktb.ktb_cj_community_spring_be.auth.dto;

import com.ktb.ktb_cj_community_spring_be.member.entity.Member;
import com.ktb.ktb_cj_community_spring_be.member.entity.type.MemberRoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class SignUpDto {

      @Email
      private String email;

      @NotBlank
      private String password;

      @NotBlank
      @Size(max = 10)
      private String nickname;

      @NotBlank
      private String phoneNumber;

      private String profileImageUrl;

      public static SignUpDto fromEntity(Member member) {
            return SignUpDto.builder()
                    .email(member.getEmail())
                    .password(member.getPassword())
                    .nickname(member.getNickname())
                    .profileImageUrl(member.getProfileImageUrl())
                    .build();
      }

      public static Member signUpForm(SignUpDto request, String encodedPasswordEncoder) {
            return Member.builder()
                    .email(request.getEmail())
                    .password(encodedPasswordEncoder)
                    .nickname(request.getNickname())
                    .roleType(MemberRoleType.USER)
                    .build();
      }

}
