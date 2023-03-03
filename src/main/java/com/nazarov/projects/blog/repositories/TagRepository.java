package com.nazarov.projects.blog.repositories;

import com.nazarov.projects.blog.models.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Long> {

  @Query("select t from Tag t where t.name = ?1")
  List<Tag> findByName(String name);

}
