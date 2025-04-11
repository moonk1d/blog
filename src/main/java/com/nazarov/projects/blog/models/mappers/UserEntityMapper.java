package com.nazarov.projects.blog.models.mappers;

import com.nazarov.projects.blog.dtos.CreateUserDto;
import com.nazarov.projects.blog.dtos.UserDto;
import com.nazarov.projects.blog.dtos.UserInfoDto;
import com.nazarov.projects.blog.models.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEntityMapper {

  private final ModelMapper mapper;

  private final BlogPostEntityMapper blogPostEntityMapper;


  public UserDto toDto(User user) {
    return mapper
        .map(user, UserDto.class)
        .toBuilder()
        .posts(user
            .getPosts()
            .stream()
            .map(post -> blogPostEntityMapper.toInfo(post))
            .toList())
        .build();
  }

  public UserInfoDto toInfoDto(User user) {
    return mapper.map(user, UserInfoDto.class);
  }

  public User toUserEntity(CreateUserDto userDto) {
    return mapper.map(userDto, User.class);
  }

  public User toUserEntity(UserInfoDto userDto) {
    return mapper.map(userDto, User.class);
  }

}
