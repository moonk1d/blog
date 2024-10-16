package com.nazarov.projects.blog.repositories;

import com.nazarov.projects.blog.models.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<Tag, Long> {

  @Query("SELECT t FROM Tag t WHERE t.name = :name")
  List<Tag> findByName(@Param("name") String name);

}
