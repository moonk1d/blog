package com.nazarov.projects.blog.integration.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nazarov.projects.blog.dtos.CreateUserDto;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.User;
import com.nazarov.projects.blog.repositories.BlogPostRepository;
import com.nazarov.projects.blog.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BlogPostRepository blogPostRepository;

  @Autowired
  private ObjectMapper objectMapper;
  private Long userId;

  @BeforeEach
  void setUp() {
    blogPostRepository.deleteAll();
    userRepository.deleteAll();
    userId = userRepository.save(new User("John Doe", "john.doe@example.com")).getId();
  }

  @Test
  void shouldGetAllUsers() throws Exception {
    mockMvc.perform(get("/users")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].name", is("John Doe")));
  }

  @Test
  void shouldGetUserById() throws Exception {
    mockMvc.perform(get("/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("John Doe")));
  }

  @Test
  void shouldCreateUser() throws Exception {
    CreateUserDto createUserDto = new CreateUserDto("Jane Doe", "jane.doe@example.com");

    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createUserDto)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is("Jane Doe")));
  }

  @Test
  void shouldDeleteUser() throws Exception {
    mockMvc.perform(delete("/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  @Test
  void shouldGetUserPosts() throws Exception {
    mockMvc.perform(get("/users/" + userId + "/posts")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void shouldReturnNotFoundForNonExistentUser() throws Exception {
    mockMvc.perform(get("/users/999")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnNotFoundForDeleteNonExistentUser() throws Exception {
    mockMvc.perform(delete("/users/999")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestForInvalidUserCreation() throws Exception {
    CreateUserDto invalidUserDto = new CreateUserDto("", "invalid-email");

    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidUserDto)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestForInvalidUserId() throws Exception {
    mockMvc.perform(get("/users/invalid-id")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnEmptyListWhenNoUsers() throws Exception {
    userRepository.deleteAll();

    mockMvc.perform(get("/users")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(0)));
  }

  @Test
  void shouldBeAbleToDeleteUserWithAssociatedPosts() throws Exception {
    BlogPost post = new BlogPost("Sample Post", "Sample Content", userRepository.findById(userId).orElseThrow());
    blogPostRepository.save(post);

    mockMvc.perform(delete("/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  @Test
  void shouldGetUserPostsWhenUserHasPosts() throws Exception {
    BlogPost post = new BlogPost("Sample Post", "Sample Content", userRepository.findById(userId).orElseThrow());
    blogPostRepository.save(post);

    mockMvc.perform(get("/users/" + userId + "/posts")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].subject", is("Sample Post")))
        .andExpect(jsonPath("$[0].body", is("Sample Content")));
  }
}