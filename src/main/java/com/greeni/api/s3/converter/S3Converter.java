package com.greeni.api.s3.converter;

import com.greeni.api.s3.dto.S3ResponseDTO;

public class S3Converter {

    public static S3ResponseDTO.toS3UrlDTO toS3Url(String url){
        return S3ResponseDTO.toS3UrlDTO.builder()
                .url(url).
                build();
    }
}
