package com.ktb.ktb_cj_community_spring_be.global.exception;

import com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

      private final ErrorCode errorCode;

      public GlobalException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
      }
}
