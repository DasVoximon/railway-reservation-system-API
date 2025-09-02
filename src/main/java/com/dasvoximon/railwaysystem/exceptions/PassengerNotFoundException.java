package com.dasvoximon.railwaysystem.exceptions;

public class PassengerNotFoundException extends RuntimeException {

  public PassengerNotFoundException() {
    super();
  }

  public PassengerNotFoundException(String message) {
      super(message);
  }

  public PassengerNotFoundException(Throwable cause) {
    super(cause);
  }

  public PassengerNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
