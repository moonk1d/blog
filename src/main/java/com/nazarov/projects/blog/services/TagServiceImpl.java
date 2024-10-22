package com.nazarov.projects.blog.services;

import static java.util.Objects.isNull;

import com.nazarov.projects.blog.dtos.CreateTagDto;
import com.nazarov.projects.blog.exceptions.NullIdException;
import com.nazarov.projects.blog.exceptions.ResourceNotFoundException;
import com.nazarov.projects.blog.exceptions.TagExistsException;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.Tag;
import com.nazarov.projects.blog.models.mappers.TagEntityMapper;
import com.nazarov.projects.blog.repositories.TagRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;

  private final BlogPostService blogPostService;

  private final TagEntityMapper tagEntityMapper;

  public TagServiceImpl(TagRepository tagRepository, BlogPostService blogPostService,
      TagEntityMapper tagEntityMapper) {
    this.tagRepository = tagRepository;
    this.blogPostService = blogPostService;
    this.tagEntityMapper = tagEntityMapper;
  }

  @Override
  @Transactional
  public Tag createTag(CreateTagDto createTagDto) {
    Tag tag = tagEntityMapper.toEntity(createTagDto);
    if (tagExists(tag)) {
      throw new TagExistsException(tag.getName());
    }
    return tagRepository.save(tag);
  }

  @Override
  public Tag getTag(Long id) {
    if (isNull(id)) {
      throw new NullIdException();
    }

    return tagRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id));
  }

  @Override
  public List<Tag> getTags() {
    return tagRepository.findAll();
  }

  @Override
  public Optional<Tag> getTagByName(String name) {
    return tagRepository.findByName(name);
  }

  @Override
  public boolean tagExists(Tag tag) {
    return getTagByName(tag.getName()).isPresent();
  }

  @Override
  public Page<Tag> getTags(Pageable page) {
    return tagRepository.findAll(page);
  }

  @Override
  @Transactional
  public void deleteTag(Long id) {
    if (isNull(id)) {
      throw new NullIdException();
    }

    Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    removeTagsFromPosts(tag);
    tagRepository.deleteById(id);
  }

  @Override
  @Transactional
  public Set<Tag> resolveTags(List<String> tagNames) {
    Set<Tag> tags = new HashSet<>();
    for (String name : tagNames) {
      Tag tag = getTagByName(name).orElseGet(() -> tagRepository.save(new Tag(name)));
      tags.add(tag);
    }
    return tags;
  }

  private void removeTagsFromPosts(Tag tag) {
    for (BlogPost post : tag.getPosts()) {
      post.getTags().remove(tag);
      blogPostService.updatePost(post);
    }
  }
}
