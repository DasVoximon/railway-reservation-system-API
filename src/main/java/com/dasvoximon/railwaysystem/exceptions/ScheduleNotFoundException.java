package com.dasvoximon.railwaysystem.exceptions;

public class ScheduleNotFoundException extends RuntimeException {

    public ScheduleNotFoundException() {
        super();
    }

    public ScheduleNotFoundException(String message) {
        super(message);
    }

    public ScheduleNotFoundException(Throwable cause) {
        super(cause);
    }

    public ScheduleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
