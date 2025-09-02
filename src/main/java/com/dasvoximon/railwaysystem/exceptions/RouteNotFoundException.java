package com.dasvoximon.railwaysystem.exceptions;

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
