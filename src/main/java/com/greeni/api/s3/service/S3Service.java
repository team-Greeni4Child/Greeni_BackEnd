package com.greeni.api.s3.service;

import com.greeni.api.s3.dto.S3ResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {

    S3ResponseDTO.GetS3UrlDTO upload(Long id, String file);

}
