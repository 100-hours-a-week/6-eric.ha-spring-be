package com.ktb.ktb_cj_community_spring_be.member.service;

import com.ktb.ktb_cj_community_spring_be.member.dto.MemberResponse;
import com.ktb.ktb_cj_community_spring_be.member.dto.UpdateMemberInfoRequest;
import com.ktb.ktb_cj_community_spring_be.member.dto.UpdateMemberPwdRequest;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

      MemberResponse memberInfo(String email);

      MemberResponse updateMemberNickName(UpdateMemberInfoRequest request,
              MultipartFile profileImage, String email);

      MemberResponse updateMemberPassword(UpdateMemberPwdRequest request, String email);

      boolean checkNickNameDuplication(String nickName);
}
