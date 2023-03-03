package com.nazarov.projects.blog.controllers.advices;

import com.nazarov.projects.blog.models.ErrorResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
class MessageNotReadableAdvice {

  @ResponseBody
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ErrorResponse resourceNotFoundHandler(HttpMessageNotReadableException ex) {
    log.error("Exception: ", ex);
    return ErrorResponse.builder().message(List.of(ex.getMessage())).build();
  }
}
