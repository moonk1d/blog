package com.nazarov.projects.blog.services;

import static java.util.Objects.isNull;

import com.nazarov.projects.blog.dtos.CreateBlogPostDto;
import com.nazarov.projects.blog.events.TagDeletedEvent;
import com.nazarov.projects.blog.events.UserDeletedEvent;
import com.nazarov.projects.blog.exceptions.NullIdException;
import com.nazarov.projects.blog.exceptions.ResourceNotFoundException;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.Tag;
import com.nazarov.projects.blog.models.User;
import com.nazarov.projects.blog.models.mappers.BlogPostEntityMapper;
import com.nazarov.projects.blog.repositories.BlogPostRepository;
import java.util.List;
import java.util.Set;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogPostServiceImpl implements BlogPostService {

  private final BlogPostRepository blogPostRepository;
  private final TagService tagService;
  private final UserService userService;
  private final BlogPostEntityMapper blogPostEntityMapper;

  public BlogPostServiceImpl(BlogPostRepository blogPostRepository,
      TagService tagService, UserService userService, BlogPostEntityMapper blogPostEntityMapper) {
    this.blogPostRepository = blogPostRepository;
    this.tagService = tagService;
    this.userService = userService;
    this.blogPostEntityMapper = blogPostEntityMapper;
  }

  @Override
  public Page<BlogPost> getPosts(Pageable pageable) {
    return blogPostRepository.findAll(pageable);
  }

  @Override
  public BlogPost getPostDetails(Long id) {
    if (isNull(id)) {
      throw new NullIdException();
    }

    return blogPostRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id));
  }

  @Override
  @Transactional
  public BlogPost createPost(CreateBlogPostDto createBlogPostDto) {
    User user = userService.getUser(createBlogPostDto.getAuthor().getId());
    Set<Tag> tags = tagService.resolveTags(createBlogPostDto.getTags());

    BlogPost post = blogPostEntityMapper.toEntity(createBlogPostDto, user, tags);
    return blogPostRepository.save(post);
  }

  @Override
  @Transactional
  public void deletePost(Long id) {
    if (isNull(id)) {
      throw new NullIdException();
    }

    blogPostRepository.delete(getPostDetails(id));
  }

  @EventListener
  public void onUserDeleted(UserDeletedEvent event) {
    Long userId = event.getUserId();
    List<BlogPost> posts = blogPostRepository.findByAuthorId(userId);
    for (BlogPost post : posts) {
      post.setAuthor(null);
      blogPostRepository.save(post);
    }
  }

  @EventListener
  public void onTagDeleted(TagDeletedEvent event) {
    Long tagId = event.getTagId();
    List<BlogPost> posts = blogPostRepository.findAllByTagId(tagId);
    for (BlogPost post : posts) {
      post.getTags().removeIf(tag -> tag.getId().equals(tagId));
      blogPostRepository.save(post);
    }
  }
}
