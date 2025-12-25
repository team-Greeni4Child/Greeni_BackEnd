package com.greeni.api.s3.dto;

import lombok.*;

public class S3ResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class GetS3UrlDTO {
        String url;
        String key;
    }
}
