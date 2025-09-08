package com.dasvoximon.railwaysystem.exception;

public class SeatAlreadyTakenException extends RuntimeException {

  public SeatAlreadyTakenException() {
    super();
  }

  public SeatAlreadyTakenException(String message) {
        super(message);
  }

  public SeatAlreadyTakenException(Throwable cause) {
    super(cause);
  }

  public SeatAlreadyTakenException(String message, Throwable cause) {
    super(message, cause);
  }

}
