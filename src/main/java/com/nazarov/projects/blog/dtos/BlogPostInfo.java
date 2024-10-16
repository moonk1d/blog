package com.nazarov.projects.blog.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
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
  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  private List<String> tags;

}
