package com.nazarov.projects.blog.services;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.nazarov.projects.blog.dtos.UserInfo;
import com.nazarov.projects.blog.exceptions.ResourceNotFoundException;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.User;
import com.nazarov.projects.blog.repositories.UserRepository;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final BlogPostService blogPostService;

  public UserServiceImpl(UserRepository userRepository, BlogPostService blogPostService) {
    this.userRepository = userRepository;
    this.blogPostService = blogPostService;
  }

  @Override
  public User createUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public User createUser(UserInfo userInfo) {
    return userRepository.save(
        new User(userInfo.getName(), userInfo.getNickName()));
  }

  @Override
  public User getUser(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id));
  }

  @Override
  public User getUser(UserInfo userInfo) {
    if (isNull(userInfo.getId())) {
      throw new ResourceNotFoundException();
    }

    return getUser(userInfo.getId());
  }

  @Override
  @Transactional
  public void deleteUser(Long id) {
    User user = getUser(id);
    removeUserFromPosts(user);
    userRepository.delete(user);
  }

  @Override
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @Override
  public Page<User> getUsers(Pageable page) {
    return userRepository.findAll(page);
  }

  @Override
  public Set<BlogPost> getUserPosts(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id))
        .getPosts();
  }

  private void removeUserFromPosts(User user) {
    Set<BlogPost> posts = user.getPosts();
    if (nonNull(posts)) {
      for (BlogPost post : posts) {
        post.setAuthor(null);
        blogPostService.updatePost(post);
      }
    }
  }
}
