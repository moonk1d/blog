package com.nazarov.projects.blog.models.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.nazarov.projects.blog.dtos.BlogPostInfoDto;
import com.nazarov.projects.blog.dtos.CreateUserDto;
import com.nazarov.projects.blog.dtos.UserDto;
import com.nazarov.projects.blog.dtos.UserInfoDto;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class UserEntityMapperTest {

  private UserEntityMapper mapper;
  private ModelMapper modelMapper;
  private BlogPostEntityMapper blogPostEntityMapper;

  @BeforeEach
  void setUp() {
    modelMapper = mock(ModelMapper.class);
    blogPostEntityMapper = mock(BlogPostEntityMapper.class);
    mapper = new UserEntityMapper(modelMapper, blogPostEntityMapper);
  }

  @Test
  void shouldMapUserToDto() {
    BlogPost post = BlogPost.builder()
        .id(1L)
        .subject("Sample Post")
        .body("Sample Content")
        .build();

    User user = User.builder()
        .id(1L)
        .name("John Doe")
        .nickName("johndoe")
        .createdDate(LocalDateTime.now())
        .lastModifiedDate(LocalDateTime.now())
        .posts(Set.of(post))
        .build();

    BlogPostInfoDto postInfoDto = BlogPostInfoDto.builder()
        .id(1L)
        .subject("Sample Post")
        .body("Sample Content")
        .build();

    UserDto expectedDto = UserDto.builder()
        .id(1L)
        .name("John Doe")
        .nickName("johndoe")
        .createdDate(user.getCreatedDate())
        .lastModifiedDate(user.getLastModifiedDate())
        .posts(List.of(postInfoDto))
        .build();

    when(modelMapper.map(user, UserDto.class)).thenReturn(expectedDto);
    when(blogPostEntityMapper.toInfo(post)).thenReturn(postInfoDto);

    UserDto actualDto = mapper.toDto(user);

    assertEquals(expectedDto, actualDto);
  }

  @Test
  void shouldMapUserToInfoDto() {
    User user = User.builder()
        .id(1L)
        .name("John Doe")
        .nickName("johndoe")
        .createdDate(LocalDateTime.now())
        .lastModifiedDate(LocalDateTime.now())
        .build();

    UserInfoDto expectedInfoDto = UserInfoDto.builder()
        .id(1L)
        .name("John Doe")
        .nickName("johndoe")
        .createdDate(user.getCreatedDate())
        .lastModifiedDate(user.getLastModifiedDate())
        .build();

    when(modelMapper.map(user, UserInfoDto.class)).thenReturn(expectedInfoDto);

    UserInfoDto actualInfoDto = mapper.toInfoDto(user);

    assertEquals(expectedInfoDto, actualInfoDto);
  }

  @Test
  void shouldMapCreateUserDtoToEntity() {
    CreateUserDto createUserDto = CreateUserDto.builder()
        .name("Jane Doe")
        .nickName("janedoe")
        .build();

    User expectedEntity = User.builder()
        .name("Jane Doe")
        .nickName("janedoe")
        .build();

    when(modelMapper.map(createUserDto, User.class)).thenReturn(expectedEntity);

    User actualEntity = mapper.toUserEntity(createUserDto);

    assertEquals(expectedEntity, actualEntity);
  }
}