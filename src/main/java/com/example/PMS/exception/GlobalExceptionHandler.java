package com.example.PMS.exception;

import com.example.PMS.dto.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Map<String ,String>>> handleValidationException(
            MethodArgumentNotValidException exception
    ){
        Map<String,String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error ->{
                    String fieldError = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldError,errorMessage);
                });
        return new ResponseEntity<>(
                BaseResponse.<Map<String ,String>>builder()
                        .success(false)
                        .message("Validation Error")
                        .data(errors)
                        .time(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleResourceNotFound(
            ResourceNotFoundException exception
    ){

        return new ResponseEntity<>(
                BaseResponse.errors("Error : "+ exception.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponse<Void>> handleBadRequestException(
            BadRequestException exception
    ){
        return new ResponseEntity<>(
                BaseResponse.errors("Error : " + exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
