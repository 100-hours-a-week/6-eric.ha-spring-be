package com.ktb.ktb_cj_community_spring_be.auth.web;

import com.ktb.ktb_cj_community_spring_be.auth.dto.LogoutDto;
import com.ktb.ktb_cj_community_spring_be.auth.dto.ReissueDto;
import com.ktb.ktb_cj_community_spring_be.auth.dto.SignInDto;
import com.ktb.ktb_cj_community_spring_be.auth.dto.SignUpDto;
import com.ktb.ktb_cj_community_spring_be.auth.service.AuthService;
import com.ktb.ktb_cj_community_spring_be.global.util.jwt.dto.TokenDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

      private final AuthService authService;
      // private final MailService mailService;


      @PostMapping(path = "/signUp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
              produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<SignUpDto> signUp(@RequestPart("request") SignUpDto request,
              @RequestPart(name = "image", required = false) MultipartFile image) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(authService.signUp(request, image));
      }

      @PostMapping("/signIn")
      public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto request) {
            return ResponseEntity.ok(authService.signIn(request));
      }

      @PostMapping("/logout")
      public ResponseEntity<?> logout(@RequestBody LogoutDto request) {

            authService.logout(request);
            return ResponseEntity.status(HttpStatus.OK).body("로그아웃 성공");
      }

      @PostMapping("/reissue")
      public ResponseEntity<TokenDto> reissue(@Valid @RequestBody ReissueDto request) {

            return ResponseEntity.ok(authService.reissue(request));
      }


}
