package com.nazarov.projects.blog.controllers.advices;

import static java.lang.String.format;

import com.nazarov.projects.blog.controllers.exceptions.ResourceNotFoundException;
import com.nazarov.projects.blog.models.ErrorResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
class ResourceNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ErrorResponse resourceNotFoundHandler(ResourceNotFoundException ex) {
    log.error("Exception: ", ex);
    return ErrorResponse.builder().message(List.of(ex.getMessage())).build();
  }
}
