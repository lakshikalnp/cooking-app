package com.example.cookingapp.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {

    private String errorCode;
    private String message;
    private int status;
    private String path;
    private LocalDateTime timestamp;

    public ApiErrorResponse(
            String errorCode,
            String message,
            int status,
            String path) {

        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}
