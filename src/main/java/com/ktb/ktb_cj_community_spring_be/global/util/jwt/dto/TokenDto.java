package com.ktb.ktb_cj_community_spring_be.global.util.jwt.dto;

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
public class TokenDto {

      private String accessToken;
      private String refreshToken;
      private Long accessTokenExpireTime;
      private Long refreshTokenExpireTime;
}
