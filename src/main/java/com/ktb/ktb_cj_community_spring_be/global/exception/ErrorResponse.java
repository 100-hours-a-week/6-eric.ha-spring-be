package com.ktb.ktb_cj_community_spring_be.global.exception;


import com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse {

      private ErrorCode errorCode;
      private HttpStatus httpStatus;
      private String errorMessage;
}
