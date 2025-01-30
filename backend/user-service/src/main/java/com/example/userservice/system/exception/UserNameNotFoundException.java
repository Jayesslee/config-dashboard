package com.example.userservice.system.exception;

import java.util.Map;

/**
 * @author xiao
 */
public class UserNameNotFoundException extends BaseException {
    public UserNameNotFoundException(Map<String, Object> data) {
        super(ErrorCode.USER_NAME_NOT_FOUND, data);
    }
}
