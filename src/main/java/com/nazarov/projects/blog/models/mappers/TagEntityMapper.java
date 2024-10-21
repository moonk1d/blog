package com.nazarov.projects.blog.models.mappers;

import com.nazarov.projects.blog.dtos.TagDto;
import com.nazarov.projects.blog.models.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TagEntityMapper {

  private final ModelMapper mapper;

  public TagEntityMapper(ModelMapper mapper) {
    this.mapper = mapper;
  }

  public TagDto toDto(Tag tag) {
    return mapper.map(tag, TagDto.class);
  }

  public Tag toEntity(TagDto tagDto) {
    return mapper.map(tagDto, Tag.class);
  }

}
