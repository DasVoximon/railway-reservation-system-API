package com.dasvoximon.railwaysystem.exception;

public class RouteNotFoundException extends RuntimeException {

    public RouteNotFoundException() {
        super();
    }

    public RouteNotFoundException(String message) {
      super(message);
    }

    public RouteNotFoundException(Throwable cause) {
      super(cause);
    }

    public RouteNotFoundException(String message, Throwable cause) {
      super(message, cause);
    }

}
