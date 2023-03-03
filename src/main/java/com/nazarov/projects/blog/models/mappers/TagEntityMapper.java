package com.nazarov.projects.blog.models.mappers;

import com.nazarov.projects.blog.models.Tag;
import com.nazarov.projects.blog.models.dtos.TagDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagEntityMapper {

  @Autowired
  private ModelMapper mapper;

  public TagDto toDto(Tag tag) {
    return mapper.map(tag, TagDto.class);
  }

  public Tag toEntity(TagDto tagDto) {
    return mapper.map(tagDto, Tag.class);
  }

}
