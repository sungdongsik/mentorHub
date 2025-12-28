package com.mentorHub.common;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

  private final HttpStatus httpStatus;

  public BusinessException(HttpStatus status, String message) {
    super(message);
    this.httpStatus = status;
  }

}
