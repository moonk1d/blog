package com.nazarov.projects.blog.integration.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nazarov.projects.blog.controllers.BlogPostController;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.Tag;
import com.nazarov.projects.blog.models.User;
import com.nazarov.projects.blog.dtos.BlogPostDto;
import com.nazarov.projects.blog.dtos.UserInfoDto;
import com.nazarov.projects.blog.models.mappers.BlogPostEntityMapper;
import com.nazarov.projects.blog.models.mappers.UserEntityMapper;
import com.nazarov.projects.blog.services.BlogPostService;
import com.nazarov.projects.blog.services.TagService;
import com.nazarov.projects.blog.services.UserService;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(BlogPostController.class)
class BlogPostIntegrationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private BlogPostService service;

  @MockBean
  private UserService userService;

  @MockBean
  private TagService tagService;

  @MockBean
  private BlogPostEntityMapper blogPostMapper;

  @MockBean
  private UserEntityMapper userMapper;

  @Test
  void getPosts() throws Exception {
    List<BlogPost> posts = List.of(new BlogPost("subject", "body", null));
    BlogPostDto postDto = BlogPostDto
        .builder()
        .id(1L)
        .subject("subject")
        .body("body")
        .author(
            UserInfoDto.builder().id(1L).name("user").nickName("userok").build())
        .tags(List.of("tag"))
        .build();
    Page<BlogPost> page = new PageImpl<>(posts);

    when(service.getPosts(Mockito.any())).thenReturn(page);
    when(blogPostMapper.toDto(Mockito.any())).thenReturn(postDto);

    RequestBuilder request =
        MockMvcRequestBuilders.get("/posts").contentType(APPLICATION_JSON);

    mockMvc.perform(request).andDo(print()).andExpect(status().isOk());
  }

  @Test
  void getPostDetails() throws Exception {
    BlogPost post = BlogPost
        .builder()
        .id(1L)
        .subject("subject")
        .body("body")
        .author(User.builder().id(1L).name("user").nickName("userok").build())
        .tags(Set.of(Tag.builder().id(1L).name("tag").build()))
        .build();

    BlogPostDto postDto = BlogPostDto
        .builder()
        .id(1L)
        .subject("subject")
        .body("body")
        .author(
            UserInfoDto.builder().id(1L).name("user").nickName("userok").build())
        .tags(List.of("tag"))
        .build();

    when(service.getPostDetails(1L)).thenReturn(post);
    when(blogPostMapper.toDto(post)).thenReturn(postDto);

    RequestBuilder request = MockMvcRequestBuilders
        .get("/posts/{id}", 1L)
        .contentType(APPLICATION_JSON);

    ResultActions result = mockMvc.perform(request);

    result
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(postDto)));
  }

  @Test
  void deletePost() {
    // TODO
  }

  @Test
  void createPost() throws Exception {
    // TODO
  }
}