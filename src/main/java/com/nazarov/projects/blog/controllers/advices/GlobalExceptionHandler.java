package com.nazarov.projects.blog.controllers.advices;

import com.nazarov.projects.blog.exceptions.NullIdException;
import com.nazarov.projects.blog.models.ErrorResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
    log.error("Exception: ", ex);
    return ErrorResponse.builder().message(List.of(ex.getMessage())).build();
  }

  @ExceptionHandler(NullIdException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleNullIdException(NullIdException ex) {
    log.error("Exception: ", ex);
    return ErrorResponse.builder().message(List.of(ex.getMessage())).build();
  }
}
