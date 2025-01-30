package com.example.configservice.exception;


import java.util.Map;

/**
 * @author xiao
 */
public class DuplicateKeyException extends BaseException {
    public DuplicateKeyException(Map<String, Object> data) {
        super(ErrorCode.DUPLICATE_KEY, data);
    }
}
