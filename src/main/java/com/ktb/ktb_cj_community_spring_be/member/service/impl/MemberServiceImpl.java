package com.ktb.ktb_cj_community_spring_be.member.service.impl;


import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.PROFILE_IMAGE_UPLOAD_ERROR;
import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.USER_NOT_FOUND;

import com.ktb.ktb_cj_community_spring_be.global.exception.GlobalException;
import com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode;
import com.ktb.ktb_cj_community_spring_be.global.util.aws.service.AwsS3Service;
import com.ktb.ktb_cj_community_spring_be.member.dto.MemberResponse;
import com.ktb.ktb_cj_community_spring_be.member.dto.UpdateMemberInfoRequest;
import com.ktb.ktb_cj_community_spring_be.member.dto.UpdateMemberPwdRequest;
import com.ktb.ktb_cj_community_spring_be.member.entity.Member;
import com.ktb.ktb_cj_community_spring_be.member.repository.MemberRepository;
import com.ktb.ktb_cj_community_spring_be.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

      private final MemberRepository memberRepository;
      private final AwsS3Service awsS3Service;
      private final PasswordEncoder passwordEncoder;

      @Transactional(readOnly = true)
      @Override
      public MemberResponse memberInfo(String email) {

            Member member = getMember(email);

            return MemberResponse.fromEntity(member);
      }

      @Transactional
      @Override
      public MemberResponse updateMemberNickName(UpdateMemberInfoRequest request,
              MultipartFile profileImage, String email) {
            Member member = getMember(email);

            String profileImageUrl = member.getProfileImageUrl();

            // 닉네임 중복 더블 체크
            if (checkNickNameDuplication(request.getNickName())) {
                  throw new GlobalException(ErrorCode.NICKNAME_DUPLICATION);
            }

            if (profileImage != null && !profileImage.isEmpty()) {
                  // 기존 이미지가 있다면 삭제
                  if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                        awsS3Service.deleteFilesUsingUrl(profileImageUrl);
                  }
                  try {
                        profileImageUrl = uploadProfileImage(profileImage);
                  } catch (Exception e) { // 이미지 업로드 중 오류 발생 시 예외 처리
                        throw new GlobalException(PROFILE_IMAGE_UPLOAD_ERROR);
                  }
            }

            // request 로 부터 값을 받아와 member 객체의 상태 변경
            request.updateMemberInfoToEntity(member, profileImageUrl);

            return MemberResponse.fromEntity(member);
      }

      /**
       * 닉네임 중복 조회
       *
       * @param nickname 닉네임 정보
       * @return 중복이 없을 경우 true
       * @throws GlobalException 닉네임 중복 시 예외 발생
       */
      // 멤버 닉네임 중복 조회
      @Override
      public boolean checkNickNameDuplication(String nickname) {
            return memberRepository.existsByNickname(nickname);
      }


      /**
       * 비밀번호 변경
       *
       * @param request 비밀번호 변경 요청
       * @param email   멤버 이메일
       * @return 변경된 멤버 정보
       */

      @Transactional
      @Override
      public MemberResponse updateMemberPassword(UpdateMemberPwdRequest request, String email) {

            Member member = getMember(email);

            if (request.getNewPassword() == null || request.getNewPassword().isEmpty()) {
                  throw new GlobalException(ErrorCode.PASSWORD_EMPTY);
            }

            String encodedPasswordEncoder = passwordEncoder.encode(request.getNewPassword());

            member.setPassword(encodedPasswordEncoder);

            return MemberResponse.fromEntity(member);
      }


      private Member getMember(String email) {
            return memberRepository.findByEmail(email).orElseThrow(()
                    -> new GlobalException(USER_NOT_FOUND));
      }

      private String uploadProfileImage(MultipartFile multipartFile) {
            String filName = awsS3Service.generateFileName(multipartFile);
            awsS3Service.uploadToS3(multipartFile, filName);

            return awsS3Service.getUrl(filName);
      }


}
