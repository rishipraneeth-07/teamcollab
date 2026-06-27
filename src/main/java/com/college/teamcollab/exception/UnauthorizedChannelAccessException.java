package com.college.teamcollab.exception;

public class UnauthorizedChannelAccessException extends RuntimeException {
    public UnauthorizedChannelAccessException(String message) {
        super(message);
    }
}
