package com.example.configservice.exception;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
public class ErrorResponse {
    private int code;
    private int status;
    private String message;
    private String path;
    private Instant timestamp;
    private final HashMap<String, Object> errorDetail = Maps.newHashMap();

    public ErrorResponse(BaseException ex, String path) {
        this(ex.getErrorCode().getCode(), ex.getErrorCode().getStatus().value(), ex.getErrorCode().getMessage(), path, ex.getData());
    }

    public ErrorResponse(ErrorCode errorCode, String path) {
        this(errorCode.getCode(), errorCode.getStatus().value(), errorCode.getMessage(), path, null);
    }

    public ErrorResponse(ErrorCode errorCode, String path, Map<String, Object> errorDetail) {
        this(errorCode.getCode(), errorCode.getStatus().value(), errorCode.getMessage(), path, errorDetail);
    }

    public ErrorResponse(int code, int status, String message, String path, Map<String, Object> errorDetail) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();
        if (!CollectionUtils.isEmpty(errorDetail)) {
            this.errorDetail.putAll(errorDetail);
        }
    }
}
