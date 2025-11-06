package com.greeni.api.apiPayload;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.greeni.api.apiPayload.status.CommonErrorStatus;
import com.greeni.api.apiPayload.status.ErrorReason;
import com.greeni.api.apiPayload.status.SuccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class CommonResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result;
    private LocalDateTime timestamp;

    // 요청 성공 - 201 OK
    public static <T> CommonResponse<T> onSuccess(T result) {
        return new CommonResponse<>(true, SuccessStatus._OK.getCode(), SuccessStatus._OK.getCode(), result, LocalDateTime.now());
    }

    // 리소스 생성 - 201 CREATED
    public static <T> CommonResponse<T> created(T result) {
        return new CommonResponse<>(true, SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(),
            result, LocalDateTime.now());
    }

    // 요청 접수 - 202 ACCEPTED
    public static <T> CommonResponse<T> accepted(T result) {
        return new CommonResponse<>(true, SuccessStatus._ACCEPTED.getCode(), SuccessStatus._ACCEPTED.getMessage(),
            result, LocalDateTime.now());
    }

    // 요청 실패 - ExceptionAdvice에서 사용
    public static <T> CommonResponse<T> onFailure(ErrorReason errorReason, T data) {
        return new CommonResponse<>(false, errorReason.getCode(), errorReason.getMessage(), data, LocalDateTime.now());
    }
}