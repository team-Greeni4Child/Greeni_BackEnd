package com.greeni.api.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.greeni.api.s3.converter.S3Converter;
import com.greeni.api.s3.dto.S3ResponseDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

	private static Date getExpiration() {
		Date expiration = new Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 10;
		expiration.setTime(expTimeMillis);
		return expiration;
	}

	public S3ResponseDTO.GetS3UrlDTO upload(Long id, String path, String file) {
		String fileName = path + "/" + id + "/" + UUID.randomUUID().toString() + "/" + file;
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
		return request;
	}

}
