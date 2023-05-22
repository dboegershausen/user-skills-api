package com.diogob.user.skills.api.handler;

import com.diogob.user.skills.domain.exception.MissingBirthDateException;
import com.diogob.user.skills.domain.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .status(status)
                .body(ApiError.builder()
                        .error(ex.getBindingResult().getFieldError().getDefaultMessage())
                        .timestamp(LocalDateTime.now())
                        .status(status.value()).build());
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ApiError> onNotFoundException(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .error(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value()).build());
    }

    @ExceptionHandler(MissingBirthDateException.class)
    protected ResponseEntity<ApiError> onMissingBirthDateException(MissingBirthDateException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiError.builder()
                        .error(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.UNPROCESSABLE_ENTITY.value()).build());
    }

}