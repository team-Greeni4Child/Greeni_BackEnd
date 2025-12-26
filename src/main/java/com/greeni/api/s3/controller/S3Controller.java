package com.greeni.api.s3.controller;

import com.greeni.api.apiPayload.CommonResponse;
import com.greeni.api.s3.controller.docs.S3ControllerDocs;
import com.greeni.api.s3.dto.S3ResponseDTO;
import com.greeni.api.s3.service.S3Service;
import com.greeni.api.security.jwt.userDetails.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/s3-presigned-url")
@RequiredArgsConstructor
public class S3Controller implements S3ControllerDocs {

	private final S3Service s3Service;

	@GetMapping()
	public CommonResponse<S3ResponseDTO.GetS3UrlDTO> uploadFile(
		@AuthenticationPrincipal CustomUserDetails customUserDetails, String file) {
		return CommonResponse.onSuccess(s3Service.upload(customUserDetails.getId(), file));
	}

}
