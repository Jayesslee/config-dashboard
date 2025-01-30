package com.example.configservice.exception;


import java.util.Map;

/**
 * @author xiao
 */
public class ConfigKeyFoundException extends BaseException {
    public ConfigKeyFoundException(Map<String, Object> data) {
        super(ErrorCode.CONFIG_KEY_NOT_FOUND, data);
    }
}
