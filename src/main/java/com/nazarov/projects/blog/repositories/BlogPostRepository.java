package com.nazarov.projects.blog.repositories;

import com.nazarov.projects.blog.models.BlogPost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

  List<BlogPost> findByAuthorId(Long authorId);

  @Query("SELECT p FROM BlogPost p JOIN p.tags t WHERE t.id = :tagId")
  List<BlogPost> findAllByTagId(Long tagId);

  @Modifying
  @Query("UPDATE BlogPost bp SET bp.author = null WHERE bp.author.id = :userId")
  void updateAuthorToNullByUserId(@Param("userId") Long userId);
}
