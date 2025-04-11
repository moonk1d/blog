package com.nazarov.projects.blog.models.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.nazarov.projects.blog.dtos.BlogPostDto;
import com.nazarov.projects.blog.dtos.BlogPostInfoDto;
import com.nazarov.projects.blog.dtos.CreateBlogPostDto;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.Tag;
import com.nazarov.projects.blog.models.User;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class BlogPostEntityMapperTest {

  private BlogPostEntityMapper mapper;
  private ModelMapper modelMapper;

  @BeforeEach
  void setUp() {
    modelMapper = mock(ModelMapper.class);
    mapper = new BlogPostEntityMapper(modelMapper);
  }

  @Test
  void shouldMapBlogPostToDto() {
    BlogPost blogPost = BlogPost.builder()
        .id(1L)
        .subject("Test Subject")
        .body("Test Body")
        .createdDate(LocalDateTime.now())
        .lastModifiedDate(LocalDateTime.now())
        .author(new User("John Doe", "john.doe@example.com"))
        .tags(Set.of(new Tag("Tag1"), new Tag("Tag2")))
        .build();

    BlogPostDto expectedDto = BlogPostDto.builder()
        .id(1L)
        .subject("Test Subject")
        .body("Test Body")
        .tags(Set.of("Tag1", "Tag2"))
        .build();

    when(modelMapper.map(blogPost, BlogPostDto.class)).thenReturn(expectedDto);

    BlogPostDto actualDto = mapper.toDto(blogPost);

    assertEquals(expectedDto, actualDto);
  }

  @Test
  void shouldMapBlogPostToInfoDto() {
    BlogPost blogPost = BlogPost.builder()
        .id(1L)
        .subject("Test Subject")
        .body("Test Body")
        .createdDate(LocalDateTime.now())
        .lastModifiedDate(LocalDateTime.now())
        .tags(Set.of(new Tag("Tag1"), new Tag("Tag2")))
        .build();

    BlogPostInfoDto expectedInfoDto = BlogPostInfoDto.builder()
        .id(1L)
        .subject("Test Subject")
        .body("Test Body")
        .tags(Set.of("Tag1", "Tag2"))
        .build();

    when(modelMapper.map(blogPost, BlogPostInfoDto.class)).thenReturn(expectedInfoDto);

    BlogPostInfoDto actualInfoDto = mapper.toInfo(blogPost);

    assertEquals(expectedInfoDto, actualInfoDto);
  }

  @Test
  void shouldMapCreateBlogPostDtoToEntity() {
    CreateBlogPostDto createDto = CreateBlogPostDto.builder()
        .subject("Test Subject")
        .body("Test Body")
        .build();

    User user = new User("John Doe", "john.doe@example.com");
    Set<Tag> tags = Set.of(new Tag("Tag1"), new Tag("Tag2"));

    BlogPost expectedEntity = BlogPost.builder()
        .subject("Test Subject")
        .body("Test Body")
        .author(user)
        .tags(tags)
        .build();

    when(modelMapper.map(createDto, BlogPost.class)).thenReturn(expectedEntity);

    BlogPost actualEntity = mapper.toEntity(createDto, user, tags);

    assertEquals(expectedEntity, actualEntity);
  }

  @Test
  void shouldMapBlogPostDtoToEntity() {
    BlogPostDto blogPostDto = BlogPostDto.builder()
        .id(1L)
        .subject("Test Subject")
        .body("Test Body")
        .createdDate(LocalDateTime.now())
        .lastModifiedDate(LocalDateTime.now())
        .build();

    BlogPost expectedEntity = BlogPost.builder()
        .id(1L)
        .subject("Test Subject")
        .body("Test Body")
        .build();

    when(modelMapper.map(blogPostDto, BlogPost.class)).thenReturn(expectedEntity);

    BlogPost actualEntity = mapper.toEntity(blogPostDto);

    assertEquals(expectedEntity, actualEntity);
  }
}