package com.nazarov.projects.blog.services;

import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.User;
import com.nazarov.projects.blog.models.dtos.UserInfo;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  User createUser(User user);

  User createUser(UserInfo userInfo);

  User getUser(Long id);

  User getUser(UserInfo userInfo);

  void deleteUser(Long id);

  List<User> getUsers();

  Page<User> getUsers(Pageable page);

  Set<BlogPost> getUserPosts(Long id);
}
