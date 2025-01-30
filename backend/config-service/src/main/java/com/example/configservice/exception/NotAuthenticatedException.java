package com.example.configservice.exception;


import java.util.Map;

/**
 * @author xiao
 */
public class NotAuthenticatedException extends BaseException {
    public NotAuthenticatedException() {
        super(ErrorCode.NOT_AUTHENTICATED);
    }
}
