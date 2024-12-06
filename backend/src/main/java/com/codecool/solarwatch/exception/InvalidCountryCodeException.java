package com.codecool.solarwatch.exception;

public class InvalidCountryCodeException extends RuntimeException {
  public InvalidCountryCodeException() {
    super("Country code should be 2-character ISO 3166 country code");
  }
}
