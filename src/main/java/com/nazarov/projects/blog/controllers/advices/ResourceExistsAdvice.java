package com.nazarov.projects.blog.controllers.advices;

import com.nazarov.projects.blog.exceptions.TagExistsException;
import com.nazarov.projects.blog.models.ErrorResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
class ResourceExistsAdvice {

  @ResponseBody
  @ExceptionHandler(TagExistsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ErrorResponse tagExistsHandler(TagExistsException ex) {
    log.error("Exception: ", ex);
    return ErrorResponse.builder().message(List.of(ex.getMessage())).build();
  }

}
