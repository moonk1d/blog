package com.nazarov.projects.blog.models.mappers;

import com.nazarov.projects.blog.models.User;
import com.nazarov.projects.blog.models.dtos.UserDto;
import com.nazarov.projects.blog.models.dtos.UserInfo;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private BlogPostEntityMapper blogPostEntityMapper;

  public UserDto toDto(User user) {
    return mapper
        .map(user, UserDto.class)
        .toBuilder()
        .posts(user
            .getPosts()
            .stream()
            .map(post -> blogPostEntityMapper.toInfo(post))
            .collect(Collectors.toList()))
        .build();
  }

  public UserInfo toInfo(User user) {
    return mapper.map(user, UserInfo.class);
  }

}
