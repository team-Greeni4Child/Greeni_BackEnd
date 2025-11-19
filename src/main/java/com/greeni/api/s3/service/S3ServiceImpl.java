package com.greeni.api.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.apiPayload.exception.GeneralException;
import com.greeni.api.apiPayload.status.S3ErrorStauts;
import com.greeni.api.s3.converter.S3Converter;
import com.greeni.api.s3.dto.S3ResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public S3ServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public S3ResponseDTO.toS3UrlDTO upload(MultipartFile file) {
        if(file.isEmpty()){
            throw new GeneralException(S3ErrorStauts.NO_IMAGE);
        }
        try {
            return S3Converter.toS3Url(uploadImage(file));
        } catch (Exception e) {
            throw new GeneralException(S3ErrorStauts.IMAGE_UPLOAD_FAIL);
        }
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename(); // 고유한 파일 이름 생성

        // 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        // S3에 파일 업로드 요청 생성
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata);

        // S3에 파일 업로드
        amazonS3.putObject(putObjectRequest);

        return getPublicUrl(fileName);
    }

    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), fileName);
    }
}
