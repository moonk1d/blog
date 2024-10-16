package com.nazarov.projects.blog.exceptions;

import static java.lang.String.format;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(Long id) {
    super(format("Resource with id=[%s] not found", id));
  }

  public ResourceNotFoundException() {
    super("Resource not found");
  }

}
