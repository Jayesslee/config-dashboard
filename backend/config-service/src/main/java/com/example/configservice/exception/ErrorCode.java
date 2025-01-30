package com.example.configservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    NOT_AUTHENTICATED(9001, HttpStatus.UNAUTHORIZED, "User is not authenticated"),
    UNAUTHORIZED(9002, HttpStatus.FORBIDDEN, "The user is not authorized to perform this action."),
    Role_NOT_FOUND(1002, HttpStatus.NOT_FOUND, "role not found!"),
    CONFIG_KEY_NOT_FOUND(3001, HttpStatus.BAD_REQUEST, "config key not found."),
    METHOD_ARGUMENT_NOT_VALID(3003, HttpStatus.BAD_REQUEST, "invalid method argument."),
    DUPLICATE_KEY(3004, HttpStatus.BAD_REQUEST, "duplicate key.");
    private final int code;

    private final HttpStatus status;

    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
