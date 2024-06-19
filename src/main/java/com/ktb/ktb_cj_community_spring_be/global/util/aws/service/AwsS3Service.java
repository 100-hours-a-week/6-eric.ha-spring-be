package com.ktb.ktb_cj_community_spring_be.global.util.aws.service;

import static com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode.INTERNAL_SERVER_ERROR;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ktb.ktb_cj_community_spring_be.global.exception.GlobalException;
import com.ktb.ktb_cj_community_spring_be.global.exception.type.ErrorCode;
import com.ktb.ktb_cj_community_spring_be.global.util.aws.dto.S3ImageDto;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {

      private final AmazonS3 amazonS3;

      @Value("${cloud.aws.s3.bucket}")
      private String bucketName;


      public S3ImageDto uploadImage(MultipartFile multipartfile) {

            String fileName = generateFileName(multipartfile);
            uploadToS3(multipartfile, fileName);

            return S3ImageDto.builder()
                    .url(getUrl(fileName))
                    .fileName(fileName)
                    .size(multipartfile.getSize())
                    .build();

      }

      /**
       * AWS S3에 파일을 업로드.
       *
       * @param multipartFile 업로드할 파일
       * @param fileName      파일 이름
       */
      public void uploadToS3(MultipartFile multipartFile, String fileName) {
            try (InputStream inputStream = multipartFile.getInputStream()) {
                  amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream,
                          getObjectMetadata(multipartFile))
                          .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                  log.error("IOException is occurred", e);
                  throw new GlobalException(INTERNAL_SERVER_ERROR);
            }
      }


      public void deleteFile(String fileName) {
            try {
                  amazonS3.deleteObject(bucketName, fileName);
            } catch (AmazonS3Exception e) {
                  log.error("AmazonS3Exception is occurred", e);
                  throw new GlobalException(INTERNAL_SERVER_ERROR);
            }
      }

      public String getUrl(String fileName) {
            return amazonS3.getUrl(bucketName, fileName).toString();
      }

      public ObjectMetadata getObjectMetadata(MultipartFile multipartFile) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());
            return objectMetadata;
      }


      public String generateFileName(MultipartFile multipartFile) {
            return UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
      }


      // URL 을 사용하여 파일을 삭제하는 메서드
      public void deleteFilesUsingUrl(String fileUrl) {

            String fileName = extractFileNameFromUrl(fileUrl);
            deleteFile(fileName); // 기존에 작성한 deleteFile 메서드를 재사용
      }

      public String extractFileNameFromUrl(String fileUrl) {
            return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
      }

}
