package com.abtech.exception;

public class MissingArgumentException extends RuntimeException{
    public MissingArgumentException(String message) {
        super(message);
    }

    public MissingArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
