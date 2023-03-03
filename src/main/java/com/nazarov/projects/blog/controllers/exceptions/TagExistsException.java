package com.nazarov.projects.blog.controllers.exceptions;

import static java.lang.String.format;

public class TagExistsException extends RuntimeException {

  public TagExistsException(String name) {
    super(format("Tag [%s] already exists", name));
  }
}
