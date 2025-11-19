package com.greeni.api.s3.service;

import com.greeni.api.s3.dto.S3ResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {

    String uploadImage(MultipartFile file) throws IOException;

    S3ResponseDTO.toS3UrlDTO upload(MultipartFile file);
}
