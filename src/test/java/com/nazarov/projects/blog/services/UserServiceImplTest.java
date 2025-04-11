package com.nazarov.projects.blog.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nazarov.projects.blog.dtos.CreateUserDto;
import com.nazarov.projects.blog.dtos.UserInfoDto;
import com.nazarov.projects.blog.events.UserDeletedEvent;
import com.nazarov.projects.blog.exceptions.NullIdException;
import com.nazarov.projects.blog.exceptions.ResourceNotFoundException;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.User;
import com.nazarov.projects.blog.models.mappers.UserEntityMapper;
import com.nazarov.projects.blog.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserEntityMapper userMapper;

  @Mock
  private ApplicationEventPublisher publisher;

  @InjectMocks
  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createUser_ShouldSaveUser_WhenDtoIsValid() {
    CreateUserDto createUserDto = new CreateUserDto();
    User user = new User();
    User savedUser = new User();
    UserInfoDto userInfoDto = new UserInfoDto();

    when(userMapper.toUserEntity(createUserDto)).thenReturn(user);
    when(userRepository.save(user)).thenReturn(savedUser);
    when(userMapper.toInfoDto(savedUser)).thenReturn(userInfoDto);

    UserInfoDto result = userService.createUser(createUserDto);

    assertNotNull(result);
    assertEquals(userInfoDto, result);
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void getUser_ShouldReturnUser_WhenUserInfoDtoIsValid() {
    UserInfoDto userInfoDto = new UserInfoDto();
    userInfoDto.setId(1L);

    User user = new User();
    when(userRepository.findById(userInfoDto.getId())).thenReturn(Optional.of(user));

    User result = userService.getUser(userInfoDto);

    assertNotNull(result);
    assertEquals(user, result);
    verify(userRepository, times(1)).findById(userInfoDto.getId());
  }

  @Test
  void getUser_ShouldThrowException_WhenUserInfoDtoIdIsNull() {
    UserInfoDto userInfoDto = new UserInfoDto();
    userInfoDto.setId(null);

    assertThrows(NullIdException.class, () -> userService.getUser(userInfoDto));
  }

  @Test
  void getUser_ShouldReturnUser_WhenIdIsValid() {
    Long userId = 1L;
    User user = new User();
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    User result = userService.getUser(userId);

    assertNotNull(result);
    assertEquals(user, result);
    verify(userRepository, times(1)).findById(userId);
  }

  @Test
  void getUser_ShouldThrowException_WhenIdIsNull() {
    assertThrows(NullIdException.class, () -> userService.getUser((Long) null));
  }

  @Test
  void getUser_ShouldThrowException_WhenUserNotFound() {
    Long userId = 1L;
    assertThrows(ResourceNotFoundException.class, () -> userService.getUser(userId));
  }

  @Test
  void deleteUser_ShouldDeleteUser_WhenIdIsValid() {
    Long userId = 1L;
    User user = new User();
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    userService.deleteUser(userId);

    verify(userRepository, times(1)).delete(user);
    verify(publisher, times(1)).publishEvent(any(UserDeletedEvent.class));
  }

  @Test
  void deleteUser_ShouldThrowException_WhenIdIsNull() {
    Long userId = 1L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(NullIdException.class, () -> userService.deleteUser(null));
  }

  @Test
  void getUsers_ShouldReturnAllUsers() {
    List<User> users = List.of(new User(), new User());
    when(userRepository.findAll()).thenReturn(users);

    List<User> result = userService.getUsers();

    assertEquals(2, result.size());
    verify(userRepository, times(1)).findAll();
  }

  @Test
  void getUsers_ShouldReturnPagedUsers() {
    Pageable pageable = Pageable.ofSize(10);
    List<User> users = List.of(new User(), new User());
    Page<User> page = new PageImpl<>(users);

    when(userRepository.findAll(pageable)).thenReturn(page);

    Page<User> result = userService.getUsers(pageable);

    assertEquals(2, result.getContent().size());
    verify(userRepository, times(1)).findAll(pageable);
  }

  @Test
  void getUserPosts_ShouldReturnPosts_WhenIdIsValid() {
    Long userId = 1L;
    Set<BlogPost> posts = Set.of(new BlogPost());
    User user = new User();
    user.setPosts(posts);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    Set<BlogPost> result = userService.getUserPosts(userId);

    assertEquals(posts, result);
    verify(userRepository, times(1)).findById(userId);
  }

  @Test
  void getUserPosts_ShouldThrowException_WhenIdIsNull() {
    assertThrows(NullIdException.class, () -> userService.getUserPosts(null));
  }

  @Test
  void getUserPosts_ShouldThrowException_WhenUserNotFound() {
    Long userId = 1L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> userService.getUserPosts(userId));
  }
}