package com.ktb.ktb_cj_community_spring_be.auth.service;

import com.ktb.ktb_cj_community_spring_be.auth.dto.LogoutDto;
import com.ktb.ktb_cj_community_spring_be.auth.dto.ReissueDto;
import com.ktb.ktb_cj_community_spring_be.auth.dto.SignInDto;
import com.ktb.ktb_cj_community_spring_be.auth.dto.SignUpDto;
import com.ktb.ktb_cj_community_spring_be.global.util.jwt.dto.TokenDto;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {

      SignUpDto signUp(SignUpDto request, MultipartFile multipartFile);

      TokenDto signIn(SignInDto request);

      void logout(LogoutDto request);

      TokenDto reissue(ReissueDto request);


}
