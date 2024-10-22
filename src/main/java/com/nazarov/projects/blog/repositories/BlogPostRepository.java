package com.nazarov.projects.blog.repositories;

import com.nazarov.projects.blog.models.BlogPost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

  List<BlogPost> findByAuthorId(Long authorId);

  @Query("SELECT p FROM BlogPost p JOIN p.tags t WHERE t.id = :tagId")
  List<BlogPost> findAllByTagId(Long tagId);
}
