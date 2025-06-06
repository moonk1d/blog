package com.nazarov.projects.blog.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.nazarov.projects.blog.dtos.BlogPostDto;
import com.nazarov.projects.blog.dtos.CreateTagDto;
import com.nazarov.projects.blog.dtos.PageDTO;
import com.nazarov.projects.blog.dtos.TagDto;
import com.nazarov.projects.blog.models.mappers.BlogPostEntityMapper;
import com.nazarov.projects.blog.models.mappers.TagEntityMapper;
import com.nazarov.projects.blog.services.TagService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/tags")
public class TagController {

  @Autowired
  private TagService tagService;

  @Autowired
  private TagEntityMapper tagMapper;

  @Autowired
  private BlogPostEntityMapper postEntityMapper;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public PageDTO<TagDto> getTags(Pageable page) {
    return PageDTO.create(
        tagService.getTags(page).map(tag -> tagMapper.toDto(tag)));
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
  public TagDto getTagDetails(@PathVariable(value = "id") @NotNull Long id) {
    return tagMapper.toDto(tagService.getTag(id));
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "{id}/posts", produces = APPLICATION_JSON_VALUE)
  public List<BlogPostDto> getPostsForTag(@PathVariable(value = "id") @NotNull Long id) {
    return tagService
        .getTagWithPosts(id)
        .getPosts()
        .stream()
        .map(post -> postEntityMapper.toDto(post))
        .collect(Collectors.toList());
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
  public void deleteTag(@PathVariable(value = "id") @NotNull Long id) {
    tagService.deleteTag(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public TagDto createTag(@Valid @RequestBody CreateTagDto createTagDto) {
    return tagMapper.toDto(tagService.createTag(createTagDto));
  }
}
