package com.nazarov.projects.blog.services;

import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.dtos.BlogPostDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogPostService {

  Page<BlogPost> getPosts(Pageable pageable);

  BlogPost getPostDetails(Long id);

  BlogPost createPost(BlogPost blogPost);

  void deletePost(Long id);
}
