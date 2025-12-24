package com.greeni.api.s3.converter;

import com.greeni.api.s3.dto.S3ResponseDTO;

public class S3Converter {

    public static S3ResponseDTO.GetS3UrlDTO toS3Url(String presignedUrl, String file){
        return S3ResponseDTO.GetS3UrlDTO.builder()
                .url(presignedUrl)
                .key(file)
                .build();
    }
}
