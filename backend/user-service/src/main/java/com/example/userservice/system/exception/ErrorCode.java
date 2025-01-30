package com.example.userservice.system.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NAME_ALREADY_EXIST(1001, HttpStatus.BAD_REQUEST, "Username already exist!"),
    Role_NOT_FOUND(1002, HttpStatus.NOT_FOUND, "role not found!"),
    USER_NAME_NOT_FOUND(1002, HttpStatus.NOT_FOUND, "username not found!"),
    VERIFY_JWT_FAILED(1003, HttpStatus.UNAUTHORIZED, "failed to verify JWT!"),
    METHOD_ARGUMENT_NOT_VALID(1003, HttpStatus.BAD_REQUEST, "method argument not valid!"),;
    private final int code;

    private final HttpStatus status;

    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
