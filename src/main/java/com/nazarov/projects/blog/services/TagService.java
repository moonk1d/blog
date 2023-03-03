package com.nazarov.projects.blog.services;

import com.nazarov.projects.blog.models.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

  Tag createTag(Tag Tag);

  Tag getTag(Long id);

  void deleteTag(Long id);

  List<Tag> getTags();

  Optional<Tag> getTagByName(String name);

  boolean tagExists(Tag tag);

  Page<Tag> getTags(Pageable page);
}
