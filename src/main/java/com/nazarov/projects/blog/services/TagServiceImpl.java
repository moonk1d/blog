package com.nazarov.projects.blog.services;

import com.nazarov.projects.blog.exceptions.ResourceNotFoundException;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.Tag;
import com.nazarov.projects.blog.repositories.TagRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;

  private final BlogPostService blogPostService;

  public TagServiceImpl(TagRepository tagRepository, BlogPostService blogPostService) {
    this.tagRepository = tagRepository;
    this.blogPostService = blogPostService;
  }

  @Override
  @Transactional
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
    Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    removeTagsFromPosts(tag);
    tagRepository.deleteById(id);
  }

  private void removeTagsFromPosts(Tag tag) {
    for (BlogPost post : tag.getPosts()) {
      post.getTags().remove(tag);
      blogPostService.updatePost(post);
    }
  }
}
