package com.dasvoximon.railwaysystem.exception;

public class TrainNotFoundException extends RuntimeException {

    public TrainNotFoundException() {
        super();
    }

    public TrainNotFoundException(String message) {
        super(message);
    }

    public TrainNotFoundException(Throwable cause) {
        super(cause);
    }

    public TrainNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
