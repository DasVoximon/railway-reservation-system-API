package com.dasvoximon.railwaysystem.exception;

public class ReservationNotFoundException extends RuntimeException {

  public ReservationNotFoundException() {
    super();
  }

  public ReservationNotFoundException(String message) {
    super(message);
  }

  public ReservationNotFoundException(Throwable cause) {
    super(cause);
  }

  public ReservationNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

}
