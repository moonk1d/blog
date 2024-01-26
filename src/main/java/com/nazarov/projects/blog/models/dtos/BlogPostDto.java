package com.nazarov.projects.blog.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BlogPostDto {

  private Long id;
  @NotBlank
  private String subject;
  @NotBlank
  private String body;
  @NotNull
  private UserInfo author;

  private List<String> tags;

}
