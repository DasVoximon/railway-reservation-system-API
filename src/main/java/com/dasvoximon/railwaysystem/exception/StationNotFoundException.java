package com.dasvoximon.railwaysystem.exception;

public class StationNotFoundException extends RuntimeException {

    public StationNotFoundException() {
        super();
    }

    public StationNotFoundException(String message) {
        super(message);
    }

    public StationNotFoundException(Throwable cause) {
        super(cause);
    }

    public StationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
