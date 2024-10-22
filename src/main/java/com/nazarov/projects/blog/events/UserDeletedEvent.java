package com.nazarov.projects.blog.events;

import org.springframework.context.ApplicationEvent;

public class UserDeletedEvent extends ApplicationEvent {
  private final Long userId;

  public UserDeletedEvent(Object source, Long userId) {
    super(source);
    this.userId = userId;
  }

  public Long getUserId() {
    return userId;
  }
}