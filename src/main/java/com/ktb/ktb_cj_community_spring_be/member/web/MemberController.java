package com.ktb.ktb_cj_community_spring_be.member.web;

import com.ktb.ktb_cj_community_spring_be.auth.config.LoginUser;
import com.ktb.ktb_cj_community_spring_be.member.dto.MemberResponse;
import com.ktb.ktb_cj_community_spring_be.member.dto.UpdateMemberInfoRequest;
import com.ktb.ktb_cj_community_spring_be.member.dto.UpdateMemberPwdRequest;
import com.ktb.ktb_cj_community_spring_be.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

      private final MemberService memberService;

      @GetMapping("/myProfile")
      public ResponseEntity<MemberResponse> getMyProfile(@LoginUser String email) {
            return ResponseEntity.ok(memberService.memberInfo(email));
      }

      @PostMapping("/nicknameValidation")
      public ResponseEntity<Boolean> nicknameValidation(String nickname) {
            return ResponseEntity.ok(memberService.checkNickNameDuplication(nickname));
      }

      @PatchMapping("/myProfile/update")
      public ResponseEntity<MemberResponse> updateMyProfile(
              @RequestPart(value = "request") UpdateMemberInfoRequest request,
              @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
              @LoginUser String email) {
            return ResponseEntity.ok(
                    memberService.updateMemberNickName(request, profileImage, email));
      }

      @PatchMapping("/myProfile/update/password")
      public ResponseEntity<MemberResponse> updateMyPassword(
              @Valid @RequestBody UpdateMemberPwdRequest request,
              @LoginUser String email) {
            return ResponseEntity.ok(
                    memberService.updateMemberPassword(request, email));
      }
}
