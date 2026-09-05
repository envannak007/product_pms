package com.example.PMS.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime time;

    public static <T> BaseResponse<T> ok(String message,T data){
        return BaseResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .time(LocalDateTime.now())
                .build();
    }

    public static <T> BaseResponse<T> created (String message,T data){
        return BaseResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .time(LocalDateTime.now())
                .build();
    }

    public static <T> BaseResponse<T> errors (String message){
        return BaseResponse.<T>builder()
                .success(true)
                .message(message)
                .time(LocalDateTime.now())
                .build();
    }


}