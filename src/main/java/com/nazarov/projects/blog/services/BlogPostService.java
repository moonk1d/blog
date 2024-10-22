package com.nazarov.projects.blog.services;

import com.nazarov.projects.blog.dtos.CreateBlogPostDto;
import com.nazarov.projects.blog.models.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogPostService {

  Page<BlogPost> getPosts(Pageable pageable);

  BlogPost getPostDetails(Long id);

  BlogPost createPost(CreateBlogPostDto blogPost);

  void deletePost(Long id);

  BlogPost updatePost(BlogPost blogPost);
}
