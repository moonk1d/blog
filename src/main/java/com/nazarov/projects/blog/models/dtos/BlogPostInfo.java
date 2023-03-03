package com.nazarov.projects.blog.models.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BlogPostInfo {

  private Long id;
  private String subject;
  private String body;
  private List<String> tags;

}
