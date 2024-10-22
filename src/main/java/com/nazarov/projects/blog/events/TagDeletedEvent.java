package com.nazarov.projects.blog.events;

import org.springframework.context.ApplicationEvent;

public class TagDeletedEvent extends ApplicationEvent {
  private final Long tagId;

  public TagDeletedEvent(Object source, Long tagId) {
    super(source);
    this.tagId = tagId;
  }

  public Long getTagId() {
    return tagId;
  }
}
