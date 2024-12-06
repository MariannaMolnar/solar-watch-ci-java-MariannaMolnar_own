package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.exception.InvalidCountryCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SunriseSunsetReportControllerAdvice {

  @ResponseBody
  @ExceptionHandler(InvalidCityException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String invalidCityExceptionHandler(InvalidCityException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(InvalidCountryCodeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String invalidCountryCodeExceptionHandler(InvalidCountryCodeException ex) {
    return ex.getMessage();
  }
}
