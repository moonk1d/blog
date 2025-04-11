package com.nazarov.projects.blog.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nazarov.projects.blog.dtos.CreateBlogPostDto;
import com.nazarov.projects.blog.dtos.UserInfoDto;
import com.nazarov.projects.blog.events.TagDeletedEvent;
import com.nazarov.projects.blog.events.UserDeletedEvent;
import com.nazarov.projects.blog.exceptions.NullIdException;
import com.nazarov.projects.blog.exceptions.ResourceNotFoundException;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.Tag;
import com.nazarov.projects.blog.models.User;
import com.nazarov.projects.blog.models.mappers.BlogPostEntityMapper;
import com.nazarov.projects.blog.repositories.BlogPostRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

class BlogPostServiceImplTest {

  @Mock
  private BlogPostRepository blogPostRepository;

  @Mock
  private TagService tagService;

  @Mock
  private UserService userService;

  @Mock
  private BlogPostEntityMapper blogPostEntityMapper;

  @InjectMocks
  private BlogPostServiceImpl blogPostService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getPostDetails_ShouldReturnPost_WhenIdIsValid() {
    Long postId = 1L;
    BlogPost mockPost = new BlogPost();
    when(blogPostRepository.findById(postId)).thenReturn(Optional.of(mockPost));

    BlogPost result = blogPostService.getPostDetails(postId);

    assertNotNull(result);
    assertEquals(mockPost, result);
    verify(blogPostRepository, times(1)).findById(postId);
  }

  @Test
  void getPostDetails_ShouldThrowException_WhenIdIsNull() {
    assertThrows(NullIdException.class, () -> blogPostService.getPostDetails(null));
  }

  @Test
  void getPostDetails_ShouldThrowException_WhenPostNotFound() {
    Long postId = 1L;
    when(blogPostRepository.findById(postId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> blogPostService.getPostDetails(postId));
  }

  @Test
  void createPost_ShouldSavePost_WhenDtoIsValid() {
    CreateBlogPostDto dto = new CreateBlogPostDto();
    UserInfoDto author = new UserInfoDto();
    author.setId(1L);
    dto.setAuthor(author);

    User mockUser = new User();
    Set<Tag> mockTags = Set.of(new Tag());
    BlogPost mockPost = new BlogPost();

    when(userService.getUser(dto.getAuthor().getId())).thenReturn(mockUser);
    when(tagService.resolveTags(dto.getTags())).thenReturn(mockTags);
    when(blogPostEntityMapper.toEntity(dto, mockUser, mockTags)).thenReturn(mockPost);
    when(blogPostRepository.save(mockPost)).thenReturn(mockPost);

    BlogPost result = blogPostService.createPost(dto);

    assertNotNull(result);
    assertEquals(mockPost, result);
    verify(blogPostRepository, times(1)).save(mockPost);
  }

  @Test
  void deletePost_ShouldDeletePost_WhenIdIsValid() {
    Long postId = 1L;
    BlogPost mockPost = new BlogPost();
    when(blogPostRepository.findById(postId)).thenReturn(Optional.of(mockPost));

    blogPostService.deletePost(postId);

    verify(blogPostRepository, times(1)).delete(mockPost);
  }

  @Test
  void deletePost_ShouldThrowException_WhenIdIsNull() {
    assertThrows(NullIdException.class, () -> blogPostService.deletePost(null));
  }

  @Test
  void getPosts_ShouldReturnPagedPosts() {
    Pageable pageable = Pageable.ofSize(10);
    List<BlogPost> posts = List.of(new BlogPost(), new BlogPost());
    Page<BlogPost> page = new PageImpl<>(posts);

    when(blogPostRepository.findAll(pageable)).thenReturn(page);

    Page<BlogPost> result = blogPostService.getPosts(pageable);

    assertEquals(2, result.getContent().size());
    verify(blogPostRepository, times(1)).findAll(pageable);
  }

  @Test
  void onUserDeleted_ShouldHandleEvent_WhenUserHasPosts() {
    Long userId = 1L;

    blogPostService.onUserDeleted(new UserDeletedEvent(this, userId));

    verify(blogPostRepository, times(1)).updateAuthorToNullByUserId(userId);
  }

  @Test
  void onTagDeleted_ShouldHandleEvent_WhenTagIsAssociatedWithPosts() {
    Long tagId = 1L;
    Tag tag = new Tag();
    tag.setId(tagId);
    BlogPost post = new BlogPost();
    post.setTags(new HashSet<>(Set.of(tag)));
    List<BlogPost> posts = List.of(post);

    when(blogPostRepository.findAllByTagId(tagId)).thenReturn(posts);

    blogPostService.onTagDeleted(new TagDeletedEvent(this, tagId));

    for (BlogPost updatedPost : posts) {
      assertTrue(updatedPost.getTags().isEmpty());
      verify(blogPostRepository, times(1)).save(updatedPost);
    }
  }
}