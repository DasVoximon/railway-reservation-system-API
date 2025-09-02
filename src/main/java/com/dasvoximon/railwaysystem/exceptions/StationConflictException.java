package com.dasvoximon.railwaysystem.exceptions;

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
