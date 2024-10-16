package com.nazarov.projects.blog.services;

import com.nazarov.projects.blog.exceptions.ResourceNotFoundException;
import com.nazarov.projects.blog.models.Tag;
import com.nazarov.projects.blog.repositories.TagRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

  @Autowired
  private TagRepository tagRepository;

  @Override
  public Tag createTag(Tag tag) {
    return tagRepository.save(tag);
  }

  @Override
  public Tag getTag(Long id) {
    return tagRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id));
  }

  @Override
  public void deleteTag(Long id) {
    tagRepository.delete(tagRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id)));
  }

  @Override
  public List<Tag> getTags() {
    return tagRepository.findAll();
  }

  @Override
  public Optional<Tag> getTagByName(String name) {
    return tagRepository.findByName(name).stream().findFirst();
  }

  @Override
  public boolean tagExists(Tag tag) {
    return getTagByName(tag.getName()).isPresent();
  }

  @Override
  public Page<Tag> getTags(Pageable page) {
    return tagRepository.findAll(page);
  }
}
