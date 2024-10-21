package com.nazarov.projects.blog.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.Tag;
import com.nazarov.projects.blog.models.User;
import com.nazarov.projects.blog.dtos.BlogPostDto;
import com.nazarov.projects.blog.dtos.BlogPostInfoDto;
import com.nazarov.projects.blog.dtos.PageDTO;
import com.nazarov.projects.blog.models.mappers.BlogPostEntityMapper;
import com.nazarov.projects.blog.models.mappers.UserEntityMapper;
import com.nazarov.projects.blog.services.BlogPostService;
import com.nazarov.projects.blog.services.TagService;
import com.nazarov.projects.blog.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
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
@RequestMapping(path = "/posts")
public class BlogPostController {

  @Autowired
  private BlogPostService blogPostService;

  @Autowired
  private UserService userService;

  @Autowired
  private TagService tagService;

  @Autowired
  private BlogPostEntityMapper blogPostMapper;

  @Autowired
  private UserEntityMapper userMapper;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public PageDTO<BlogPostDto> getPosts(Pageable pageable) {
    return PageDTO.create(blogPostService
        .getPosts(pageable)
        .map(post -> blogPostMapper.toDto(post)));
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
  public BlogPostDto getPostDetails(@PathVariable(value = "id") @NotNull Long id) {
    return blogPostMapper.toDto(blogPostService.getPostDetails(id));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
  public void deletePost(@PathVariable(value = "id") @NotNull Long id) {
    blogPostService.deletePost(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public BlogPostInfoDto createPost(@Valid @RequestBody BlogPostDto blogPostDto) {
    User user = userService.getUser(blogPostDto.getAuthor());
    Set<Tag> tags = new HashSet<>();

    blogPostDto
        .getTags()
        .forEach(tag -> tags.add(tagService
            .getTagByName(tag)
            .orElseGet(() -> tagService.createTag(new Tag(tag)))));

    blogPostDto.setAuthor(userMapper.toInfoDto(user));

    BlogPost post = blogPostService.createPost(
        blogPostMapper.toEntity(blogPostDto).toBuilder().tags(tags).build());
    return blogPostMapper.toInfo(post);
  }

}
