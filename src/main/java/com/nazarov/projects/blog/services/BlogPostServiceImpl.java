package com.nazarov.projects.blog.services;

import static java.util.Objects.isNull;

import com.nazarov.projects.blog.dtos.CreateBlogPostDto;
import com.nazarov.projects.blog.exceptions.NullIdException;
import com.nazarov.projects.blog.exceptions.ResourceNotFoundException;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.Tag;
import com.nazarov.projects.blog.models.User;
import com.nazarov.projects.blog.models.mappers.BlogPostEntityMapper;
import com.nazarov.projects.blog.repositories.BlogPostRepository;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogPostServiceImpl implements BlogPostService {

  private final BlogPostRepository blogPostRepository;
  private final UserService userService;
  private final TagService tagService;

  private final BlogPostEntityMapper blogPostEntityMapper;

  public BlogPostServiceImpl(BlogPostRepository blogPostRepository, UserService userService,
      TagService tagService, BlogPostEntityMapper blogPostEntityMapper) {
    this.blogPostRepository = blogPostRepository;
    this.userService = userService;
    this.tagService = tagService;
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
  public BlogPost createPost(CreateBlogPostDto createBlogPostDto) {
    User user = userService.getUser(createBlogPostDto.getAuthor());
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

  @Override
  @Transactional
  public BlogPost updatePost(BlogPost updatedPost) {
    BlogPost existingPost = getPostDetails(updatedPost.getId());
    existingPost.setSubject(updatedPost.getSubject());
    existingPost.setBody(updatedPost.getBody());
    existingPost.setTags(updatedPost.getTags());
    existingPost.setAuthor(updatedPost.getAuthor());
    return blogPostRepository.save(existingPost);
  }
}
