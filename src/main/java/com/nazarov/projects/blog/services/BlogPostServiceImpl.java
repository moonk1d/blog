package com.nazarov.projects.blog.services;

import com.nazarov.projects.blog.controllers.exceptions.ResourceNotFoundException;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.dtos.BlogPostDto;
import com.nazarov.projects.blog.models.mappers.BlogPostEntityMapper;
import com.nazarov.projects.blog.repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BlogPostServiceImpl implements BlogPostService {

  @Autowired
  private BlogPostRepository blogPostRepository;

  @Autowired
  private BlogPostEntityMapper mapper;

  @Override
  public Page<BlogPost> getPosts(Pageable pageable) {
    return blogPostRepository.findAll(pageable);
  }

  @Override
  public BlogPost getPostDetails(Long id) {
    return blogPostRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id));
  }

  @Override
  public BlogPost createPost(BlogPost blogPost) {
    return blogPostRepository.save(blogPost);
  }

  @Override
  public void deletePost(Long id) {
    blogPostRepository.delete(getPostDetails(id));
  }
}
