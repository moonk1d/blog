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
import com.nazarov.projects.blog.dtos.CreateTagDto;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.Tag;
import com.nazarov.projects.blog.repositories.BlogPostRepository;
import com.nazarov.projects.blog.repositories.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TagControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private BlogPostRepository blogPostRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private Long tagId;

  @BeforeEach
  void setUp() {
    blogPostRepository.deleteAll();
    tagRepository.deleteAll();

    Tag tag = tagRepository.save(new Tag("Sample Tag"));
    tagId = tag.getId();
  }

  @Test
  void shouldGetAllTags() throws Exception {
    mockMvc.perform(get("/tags")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].name", is("Sample Tag")));
  }

  @Test
  void shouldGetTagDetails() throws Exception {
    mockMvc.perform(get("/tags/" + tagId)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("Sample Tag")));
  }

  @Test
  void shouldCreateTag() throws Exception {
    CreateTagDto createTagDto = CreateTagDto.builder()
        .name("New Tag")
        .build();

    mockMvc.perform(post("/tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createTagDto)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is("New Tag")));
  }

  @Test
  void shouldDeleteTag() throws Exception {
    mockMvc.perform(delete("/tags/" + tagId)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  @Test
  void shouldReturnNotFoundForNonExistentTag() throws Exception {
    mockMvc.perform(get("/tags/999")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestForInvalidTagCreation() throws Exception {
    CreateTagDto invalidTagDto = CreateTagDto.builder()
        .name("")
        .build();

    mockMvc.perform(post("/tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidTagDto)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestForInvalidTagId() throws Exception {
    mockMvc.perform(get("/tags/invalid-id")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGetPostsForTag() throws Exception {
    Tag tag = tagRepository.findById(tagId).orElseThrow();
    BlogPost post = new BlogPost("Post for Tag", "Content for Tag", null);
    post.getTags().add(tag);
    blogPostRepository.save(post);

    mockMvc.perform(get("/tags/" + tagId + "/posts")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].subject", is("Post for Tag")))
        .andExpect(jsonPath("$[0].body", is("Content for Tag")));
  }

  @Test
  void shouldReturnNotFoundForNonExistentTagInGetPosts() throws Exception {
    mockMvc.perform(get("/tags/999/posts")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound());
  }
}