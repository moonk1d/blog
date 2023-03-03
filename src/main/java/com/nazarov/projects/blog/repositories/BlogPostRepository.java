package com.nazarov.projects.blog.repositories;

import com.nazarov.projects.blog.models.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

}
