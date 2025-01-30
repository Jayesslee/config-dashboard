package com.example.userservice.system.exception;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiao
 */
@Getter
abstract class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final transient HashMap<String, Object> data = Maps.newHashMap();

    BaseException(ErrorCode errorCode, Map<String, Object> data) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    BaseException(ErrorCode errorCode, Map<String, Object> data, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        if (!CollectionUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }
}
