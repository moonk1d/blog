package com.nazarov.projects.blog.repositories;

import com.nazarov.projects.blog.models.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TagRepository extends JpaRepository<Tag, Long> {

  @Query("SELECT t FROM Tag t WHERE t.name = :name")
  Optional<Tag> findByName(@Param("name") String name);

  @Query("SELECT t FROM Tag t LEFT JOIN FETCH t.posts WHERE t.id = :tagId")
  Optional<Tag> findByIdWithPosts(@Param("tagId") Long tagId);

  @Modifying
  @Transactional
  @Query("DELETE FROM Tag t WHERE t.id = :id")
  void deleteById(@Param("id") Long id);

}
