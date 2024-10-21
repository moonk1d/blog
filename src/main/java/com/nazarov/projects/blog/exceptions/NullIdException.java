package com.nazarov.projects.blog.exceptions;

public class NullIdException extends RuntimeException {

  public NullIdException(String message) {
    super(message);
  }

  public NullIdException() {
    super("ID must not be null");
  }
}
