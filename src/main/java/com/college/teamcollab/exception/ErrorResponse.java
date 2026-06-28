package com.college.teamcollab.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
}
