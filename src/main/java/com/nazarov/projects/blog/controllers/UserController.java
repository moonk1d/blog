package com.nazarov.projects.blog.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.nazarov.projects.blog.models.dtos.BlogPostInfo;
import com.nazarov.projects.blog.models.dtos.PageDTO;
import com.nazarov.projects.blog.models.dtos.UserInfo;
import com.nazarov.projects.blog.models.mappers.BlogPostEntityMapper;
import com.nazarov.projects.blog.models.mappers.UserEntityMapper;
import com.nazarov.projects.blog.services.UserService;
import jakarta.validation.Valid;
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
@RequestMapping(path = "/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserEntityMapper userMapper;

  @Autowired
  private BlogPostEntityMapper postMapper;


  @ResponseStatus(HttpStatus.OK)
  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public PageDTO<UserInfo> getUsers(Pageable page) {
    return PageDTO.create(
        userService.getUsers(page).map(user -> userMapper.toInfo(user)));
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
  public UserInfo getUserDetails(@PathVariable(value = "id") Long id) {
    return userMapper.toInfo(userService.getUser(id));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
  public void deleteUser(@PathVariable(value = "id") Long id) {
    userService.deleteUser(id);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "{id}/posts", produces = APPLICATION_JSON_VALUE)
  public List<BlogPostInfo> getUserPosts(@PathVariable(value = "id") Long id) {
    return userService
        .getUserPosts(id)
        .stream()
        .map(post -> postMapper.toInfo(post))
        .collect(Collectors.toList());
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public UserInfo createUser(@Valid @RequestBody UserInfo user) {
    return userMapper.toInfo(userService.createUser(user));
  }
}
