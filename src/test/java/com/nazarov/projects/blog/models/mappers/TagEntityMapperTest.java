package com.nazarov.projects.blog.models.mappers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.nazarov.projects.blog.dtos.CreateTagDto;
import com.nazarov.projects.blog.dtos.TagDto;
import com.nazarov.projects.blog.models.Tag;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class TagEntityMapperTest {

  private TagEntityMapper mapper;
  private ModelMapper modelMapper;

  @BeforeEach
  void setUp() {
    modelMapper = mock(ModelMapper.class);
    mapper = new TagEntityMapper(modelMapper);
  }

  @Test
  void shouldMapTagToDto() {
    Tag tag = Tag.builder()
        .id(1L)
        .name("Sample Tag")
        .createdDate(LocalDateTime.now())
        .lastModifiedDate(LocalDateTime.now())
        .build();

    TagDto expectedDto = TagDto.builder()
        .id(1L)
        .name("Sample Tag")
        .createdDate(tag.getCreatedDate())
        .lastModifiedDate(tag.getLastModifiedDate())
        .build();

    when(modelMapper.map(tag, TagDto.class)).thenReturn(expectedDto);

    TagDto actualDto = mapper.toDto(tag);

    assertEquals(expectedDto, actualDto);
  }

  @Test
  void shouldMapCreateTagDtoToEntity() {
    CreateTagDto createTagDto = CreateTagDto.builder()
        .name("New Tag")
        .build();

    Tag expectedEntity = Tag.builder()
        .name("New Tag")
        .build();

    when(modelMapper.map(createTagDto, Tag.class)).thenReturn(expectedEntity);

    Tag actualEntity = mapper.toEntity(createTagDto);

    assertEquals(expectedEntity, actualEntity);
  }

  @Test
  void shouldMapTagToDtoWithNullValues() {
    Tag tag = Tag.builder()
        .id(null)
        .name(null)
        .createdDate(null)
        .lastModifiedDate(null)
        .build();

    TagDto expectedDto = TagDto.builder()
        .id(null)
        .name(null)
        .createdDate(null)
        .lastModifiedDate(null)
        .build();

    when(modelMapper.map(tag, TagDto.class)).thenReturn(expectedDto);

    TagDto actualDto = mapper.toDto(tag);

    assertEquals(expectedDto, actualDto);
  }
}