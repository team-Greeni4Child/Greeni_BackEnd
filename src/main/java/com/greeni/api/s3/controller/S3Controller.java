package com.greeni.api.s3.controller;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.apiPayload.exception.GeneralException;
import com.greeni.api.apiPayload.status.S3ErrorStauts;
import com.greeni.api.s3.docs.S3ControllerDocs;
import com.greeni.api.s3.dto.S3ResponseDTO;
import com.greeni.api.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/s3-presigned-url")
@RequiredArgsConstructor
public class S3Controller implements S3ControllerDocs {

    private final S3Service s3Service;

    @PostMapping("")
    public CommonResponse<S3ResponseDTO.toS3UrlDTO> uploadFile(@RequestParam("file") MultipartFile file){
        return CommonResponse.onSuccess(s3Service.upload(file));

    }
}
