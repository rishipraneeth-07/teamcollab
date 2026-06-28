package com.college.teamcollab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        buildErrorResponse(
                                HttpStatus.BAD_REQUEST,
                                ex.getMessage()
                        )
                );

    }


    @ExceptionHandler(ChannelAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleChannelAlreadyExists(
            ChannelAlreadyExistsException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        buildErrorResponse(
                                HttpStatus.CONFLICT,
                                ex.getMessage()
                        )
                );

    }


    @ExceptionHandler(ChannelNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleChannelNotFound(
            ChannelNotFoundException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        buildErrorResponse(
                                HttpStatus.NOT_FOUND,
                                ex.getMessage()
                        )
                );

    }


    @ExceptionHandler(UnauthorizedChannelAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedChannelAccess(
            UnauthorizedChannelAccessException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        buildErrorResponse(
                                HttpStatus.FORBIDDEN,
                                ex.getMessage()
                        )
                );

    }


    private ErrorResponse buildErrorResponse(
            HttpStatus status,
            String message
    ) {

        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(message)
                .build();

    }

}
