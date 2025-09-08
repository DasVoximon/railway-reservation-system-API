package com.dasvoximon.railwaysystem.exception;

public class StationConflictException extends RuntimeException {

    public StationConflictException() {
        super();
    }

    public StationConflictException(String message) {
        super(message);
    }

    public StationConflictException(Throwable cause) {
        super(cause);
    }

    public StationConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
