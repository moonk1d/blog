package com.nazarov.projects.blog.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.nazarov.projects.blog.dtos.BlogPostDto;
import com.nazarov.projects.blog.dtos.BlogPostInfoDto;
import com.nazarov.projects.blog.dtos.CreateBlogPostDto;
import com.nazarov.projects.blog.dtos.PageDTO;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.mappers.BlogPostEntityMapper;
import com.nazarov.projects.blog.services.BlogPostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
  private BlogPostEntityMapper blogPostMapper;

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
  public BlogPostInfoDto createPost(@Valid @RequestBody CreateBlogPostDto createBlogPostDto) {
    BlogPost post = blogPostService.createPost(createBlogPostDto);
    return blogPostMapper.toInfo(post);
  }

}
