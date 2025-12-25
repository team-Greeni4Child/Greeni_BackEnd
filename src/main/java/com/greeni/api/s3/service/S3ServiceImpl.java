package com.greeni.api.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
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
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public S3ServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public S3ResponseDTO.GetS3UrlDTO upload(Long id, String file){
        String fileName = "diary" + "/" + id + "/" + UUID.randomUUID().toString() + "/" + file;
        Date expiration = getExpiration();

        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePresignedUrlRequest(fileName, expiration);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        return S3Converter.toS3Url(String.valueOf(url), fileName);
    }

    private GeneratePresignedUrlRequest getGeneratePresignedUrlRequest(String fileName, Date expiration) {
        GeneratePresignedUrlRequest request
                = new GeneratePresignedUrlRequest(bucket, fileName)
                .withMethod(HttpMethod.PUT)
                .withKey(fileName)
                .withExpiration(expiration);
        return  request;
    }

    private static Date getExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 10;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

}
