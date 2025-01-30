package com.example.configservice.exception;


import java.util.Map;

/**
 * @author xiao
 */
public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }
}
