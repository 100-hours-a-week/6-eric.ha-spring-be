package com.ktb.ktb_cj_community_spring_be.auth.service;

import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.DUPLICATE_USER;
import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.INVALID_TOKEN;
import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.PASSWORD_NOT_MATCH;
import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.PROFILE_IMAGE_UPLOAD_ERROR;
import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.USER_NOT_FOUND;

import com.ktb.ktb_cj_community_spring_be.auth.dto.LogoutDto;
import com.ktb.ktb_cj_community_spring_be.auth.dto.ReissueDto;
import com.ktb.ktb_cj_community_spring_be.auth.dto.SignInDto;
import com.ktb.ktb_cj_community_spring_be.auth.dto.SignUpDto;
import com.ktb.ktb_cj_community_spring_be.global.exception.GlobalException;
import com.ktb.ktb_cj_community_spring_be.global.service.RedisService;
import com.ktb.ktb_cj_community_spring_be.global.util.aws.service.AwsS3Service;
import com.ktb.ktb_cj_community_spring_be.global.util.jwt.TokenProvider;
import com.ktb.ktb_cj_community_spring_be.global.util.jwt.dto.TokenDto;
import com.ktb.ktb_cj_community_spring_be.member.entity.Member;
import com.ktb.ktb_cj_community_spring_be.member.repository.MemberRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

      private final PasswordEncoder passwordEncoder;
      private final MemberRepository memberRepository;

      private final AwsS3Service awsS3Service;
      private final TokenProvider tokenProvider;
      private final RedisService redisService;

      private static final String REFRESH_TOKEN_PREFIX = "REFRESH_TOKEN: ";

      @Override
      @Transactional
      public SignUpDto signUp(SignUpDto request, MultipartFile profileImage) {
            try {
                  if (memberRepository.existsByEmail(request.getEmail())) {
                        throw new GlobalException(DUPLICATE_USER);
                  }

                  String encodedPasswordEncoder = passwordEncoder.encode(request.getPassword());

                  //프로필 이미지가 제공되었을 경우 S3에 업로드하고 그 URL 을 가져옴
                  String profileImageUrl = null; // 기본값은 null
                  if (profileImage != null && !profileImage.isEmpty()) {
                        try {
                              profileImageUrl = uploadProfileImage(profileImage);

                        } catch (Exception e) { // 이미지 업로드 중 오류 발생시 예외처리
                              throw new GlobalException(PROFILE_IMAGE_UPLOAD_ERROR);
                        }
                  }

                  request.setProfileImageUrl(profileImageUrl);

                  Member saveMember = memberRepository.save(
                          SignUpDto.signUpForm(request, encodedPasswordEncoder));

                  log.info("회원가입 완료: {}", saveMember.getEmail());
                  return SignUpDto.fromEntity(memberRepository.save(saveMember));
            } catch (Exception e) {
                  log.error("회원가입 중 오류", e);
                  throw new GlobalException(INTERNAL_SERVER_ERROR);
            }
      }


      @Override
      public TokenDto signIn(SignInDto request) {
            Member member = memberRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new GlobalException(USER_NOT_FOUND));

            if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
                  throw new GlobalException(PASSWORD_NOT_MATCH);
            }

            return generateToken(member.getEmail(), member.getRoleType().getCode());
      }


      /**
       * 사용자의 로그아웃 요청 처리
       *
       * @param request 로그아웃을 요청한 사용자의 accessToken 정보를 담은 객체
       */
      @Override
      public void logout(LogoutDto request) {

            // 제공된 엑세스 토큰의 유효성 검증.
            if (!tokenProvider.validateToken(request.getAccessToken())) {
                  throw new GlobalException(INVALID_TOKEN);
            }

            // 토큰이 유효하다면, Redis 에서 해당 토큰 정보를 삭제.
            if (redisService.getData(REFRESH_TOKEN_PREFIX + request.getAccessToken()) != null) {
                  redisService.deleteData(REFRESH_TOKEN_PREFIX + request.getAccessToken());
            }

            // 엑세스 토큰의 유효 시간을 확인하고 로그아웃한 토큰을 블랙리스트에 등록.
            Long expiredTime = tokenProvider.getExpireTime(request.getAccessToken());
            redisService.setDataExpire(REFRESH_TOKEN_PREFIX + request.getAccessToken(),
                    "LOGOUT", expiredTime);


      }


      /**
       * 기존의 토큰이 유효하지 않아, 새로운 토큰을 발행하는 경우에 사용
       *
       * @param request 새로운 토큰을 발행하기 위한 정보를 담고 있는 객체
       * @return 새로 발행된 토큰 정보를 담은 TokenDto 객체
       */
      @Override
      public TokenDto reissue(ReissueDto request) {

            // Redis 에서 기존의 엑세스 토큰을 키로 가지는 데이터를 가져욤.
            String data = redisService.getData(REFRESH_TOKEN_PREFIX + request.getAccessToken());

            // 저장된 데이터(value)가 없거나, 저장된 Refresh 토큰과 요청된 Refresh 토큰이 일치하지 않으면 예외 발생.
            if (!StringUtils.hasText(data) || !data.equals(request.getRefreshToken())) {
                  throw new GlobalException(INVALID_TOKEN);
            }

            // 엑세스 토큰을 이용해 사용자의 인증 정보를 가져옴
            Authentication authentication = tokenProvider.getAuthentication(
                    request.getAccessToken());

            // Redis 에서 기존의 엑세스 토큰 삭제
            redisService.deleteData(REFRESH_TOKEN_PREFIX + request.getAccessToken());

            return generateToken(authentication.getName(), getAuthorities(authentication));
      }

      /**
       * JWT 토큰 생성하고, Redis 에 저장
       *
       * @param email          사용자 이메일
       * @param memberRoleType 사용자 타입
       * @return 생성된 토큰 정보를 담은 TokenDto 객체
       */
      private TokenDto generateToken(String email, String memberRoleType) {
            TokenDto tokenDto = tokenProvider.generateToken(email, memberRoleType);

            // 생성된 토큰을 Redis 에 저장
            redisService.setDataExpire(REFRESH_TOKEN_PREFIX + tokenDto.getAccessToken(),
                    tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpireTime());

            return tokenDto;
      }

      /**
       * 인증 객체에서 권한 정보를 반환
       *
       * @param authentication 인증 객체
       * @return 인증 객체에서 가져온 권한 정보를 문자열로 변환하여 반환.
       */
      private String getAuthorities(Authentication authentication) {
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining());
      }

      /**
       * 프로필 이미지를 AWS S3에 업로드하고, 업로드된 이미지의 URL 을 반환
       *
       * @param profileImage 업로드할 이미지 파일
       * @return 업로드된 이미지의 S3 URL
       */
      private String uploadProfileImage(MultipartFile profileImage) {

            String fileName = awsS3Service.generateFileName(profileImage);
            awsS3Service.uploadToS3(profileImage, fileName);

            return awsS3Service.getUrl(fileName);
      }

}
