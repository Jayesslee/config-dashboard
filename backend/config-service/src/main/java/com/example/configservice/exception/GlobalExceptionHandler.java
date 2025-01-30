package com.example.configservice.exception;


import com.google.common.collect.ImmutableMap;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


/**
 * @author xiao
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        NotAuthenticatedException e = new NotAuthenticatedException();
        ErrorResponse errorResponse = new ErrorResponse(e, request.getRequestURI());
        log.error("occur NotAuthenticatedException");
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        UnauthorizedException e = new UnauthorizedException();
        ErrorResponse errorResponse = new ErrorResponse(e, request.getRequestURI());
        log.error("occur UnauthorizedException");
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex, request.getRequestURI());
        log.error("occur BaseException:" + errorResponse.toString());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Object> errors = new HashMap<>(8);
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.METHOD_ARGUMENT_NOT_VALID, request.getRequestURI(), errors);
        log.error("occur MethodArgumentNotValidException:" + errorResponse.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = ConfigKeyFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNameAlreadyExistException(ConfigKeyFoundException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex, request.getRequestURI());
        log.error("occur ConfigKeyFoundException:" + errorResponse.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateKeyException(DuplicateKeyException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex, request.getRequestURI());
        log.error("occur DuplicateKeyException:" + errorResponse.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


}
