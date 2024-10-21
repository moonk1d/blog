package com.nazarov.projects.blog.services;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.nazarov.projects.blog.dtos.CreateUserDto;
import com.nazarov.projects.blog.dtos.UserInfoDto;
import com.nazarov.projects.blog.exceptions.NullIdException;
import com.nazarov.projects.blog.exceptions.ResourceNotFoundException;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.User;
import com.nazarov.projects.blog.models.mappers.UserEntityMapper;
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

  private final UserEntityMapper userMapper;

  public UserServiceImpl(UserRepository userRepository, BlogPostService blogPostService,
      UserEntityMapper userMapper) {
    this.userRepository = userRepository;
    this.blogPostService = blogPostService;
    this.userMapper = userMapper;
  }

  @Override
  public UserInfoDto createUser(CreateUserDto createUserDto) {
    User user = userMapper.toUserEntity(createUserDto);
    User savedUser = userRepository.save(user);
    return userMapper.toInfoDto(savedUser);
  }

  @Override
  public User getUser(Long id) {
    if (isNull(id)) {
      throw new NullIdException();
    }

    return userRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id));
  }

  @Override
  public User getUser(UserInfoDto userInfoDto) {
    if (isNull(userInfoDto.getId())) {
      throw new NullIdException();
    }

    return getUser(userInfoDto.getId());
  }

  @Override
  @Transactional
  public void deleteUser(Long id) {
    if (isNull(id)) {
      throw new NullIdException();
    }

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
    if (isNull(id)) {
      throw new NullIdException();
    }

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
