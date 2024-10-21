package com.nazarov.projects.blog.services;

import com.nazarov.projects.blog.dtos.CreateUserDto;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.User;
import com.nazarov.projects.blog.dtos.UserInfoDto;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  UserInfoDto createUser(CreateUserDto createUserDto);

  User getUser(Long id);

  User getUser(UserInfoDto userInfoDto);

  void deleteUser(Long id);

  List<User> getUsers();

  Page<User> getUsers(Pageable page);

  Set<BlogPost> getUserPosts(Long id);
}
