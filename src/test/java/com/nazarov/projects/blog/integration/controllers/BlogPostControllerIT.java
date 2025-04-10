package com.nazarov.projects.blog.integration.controllers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nazarov.projects.blog.dtos.CreateBlogPostDto;
import com.nazarov.projects.blog.dtos.UserInfoDto;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.User;
import com.nazarov.projects.blog.repositories.BlogPostRepository;
import com.nazarov.projects.blog.repositories.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BlogPostControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private BlogPostRepository blogPostRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private Long userId;
  private Long postId;

  @BeforeEach
  void setUp() {
    blogPostRepository.deleteAll();
    userRepository.deleteAll();

    User user = userRepository.save(new User("John Doe", "john.doe@example.com"));
    userId = user.getId();

    BlogPost post = blogPostRepository.save(new BlogPost("Sample Post", "Sample Content", user));
    postId = post.getId();
  }

  @Test
  void shouldGetAllPosts() throws Exception {
    mockMvc.perform(get("/posts")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].subject", is("Sample Post")));
  }

  @Test
  void shouldGetPostDetails() throws Exception {
    mockMvc.perform(get("/posts/" + postId)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.subject", is("Sample Post")))
        .andExpect(jsonPath("$.body", is("Sample Content")));
  }

  @Test
  void shouldCreatePost() throws Exception {
    CreateBlogPostDto createPostDto = CreateBlogPostDto.builder()
        .subject("New Post")
        .body("New Content")
        .author(UserInfoDto.builder().id(userId).build())
        .tags(List.of())
        .build();

    mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createPostDto)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.subject", is("New Post")))
        .andExpect(jsonPath("$.body", is("New Content")));
  }

  @Test
  void shouldDeletePost() throws Exception {
    mockMvc.perform(delete("/posts/" + postId)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  @Test
  void shouldReturnNotFoundForNonExistentPost() throws Exception {
    mockMvc.perform(get("/posts/999")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestForInvalidPostCreation() throws Exception {
    CreateBlogPostDto invalidPostDto = CreateBlogPostDto.builder()
        .subject("")
        .body("")
        .author(UserInfoDto.builder().id(userId).build())
        .tags(List.of())
        .build();

    mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidPostDto)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestForInvalidPostIdInGet() throws Exception {
    mockMvc.perform(get("/posts/invalid-id")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestForInvalidPostIdInDelete() throws Exception {
    mockMvc.perform(delete("/posts/invalid-id")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldCreatePostWithTags() throws Exception {
    CreateBlogPostDto createPostDto = CreateBlogPostDto.builder()
        .subject("Post with Tags")
        .body("Content with Tags")
        .author(UserInfoDto.builder().id(userId).build())
        .tags(List.of("Tag1", "Tag2"))
        .build();

    mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createPostDto)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.subject", is("Post with Tags")))
        .andExpect(jsonPath("$.tags", hasSize(2)))
        .andExpect(jsonPath("$.tags", containsInAnyOrder("Tag1", "Tag2")));
  }
}