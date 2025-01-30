package com.example.userservice.system.exception;

import java.util.Map;

/**
 * @author xiao
 */
public class RoleNotFoundException extends BaseException {
    public RoleNotFoundException(Map<String, Object> data) {
        super(ErrorCode.Role_NOT_FOUND, data);
    }
}
