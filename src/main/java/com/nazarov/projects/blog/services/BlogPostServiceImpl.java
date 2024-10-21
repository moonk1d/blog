package com.nazarov.projects.blog.services;

import com.nazarov.projects.blog.exceptions.ResourceNotFoundException;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.repositories.BlogPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogPostServiceImpl implements BlogPostService {

  private final BlogPostRepository blogPostRepository;

  public BlogPostServiceImpl(BlogPostRepository blogPostRepository) {
    this.blogPostRepository = blogPostRepository;
  }

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
  @Transactional
  public void deletePost(Long id) {
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
