package com.etiqa.config;

import com.etiqa.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDatabaseDown(DataIntegrityViolationException exception,
                                                          HttpServletRequest request) {
        log.info("ErrorHandler.handleDataIntegrityViolationException");
        Throwable rootCause = exception.getRootCause();
        log.error("rootcause " + rootCause);
        log.error("", exception);
        var errMessage = rootCause.getMessage();
        return ApiResponse.exception(errMessage);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponse> handleDatabaseDown(ConstraintViolationException exception,
                                                          HttpServletRequest request) {
        log.info("ErrorHandler.handleConstraintViolationException");
        log.error("", exception);
        var errMessage = String.valueOf(exception);
        return ApiResponse.exception(errMessage);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException exception,
                                                                   HttpServletRequest request) {
        return ApiResponse.forbidden();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessResourceFailureException.class)
    public String databaseError(DataAccessResourceFailureException exception) {
        log.info("ExceptionHander.databaseError");
        log.error("", exception);
        return "databaseError";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public Object handleException(Exception exception, HttpServletRequest request) {
        log.info("");
        log.error("", exception);
        return ApiResponse.exception(exception);
    }

}
