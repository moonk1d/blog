package com.nazarov.projects.blog.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class BlogPostDto {

  private Long id;
  @NotBlank
  private String subject;
  @NotBlank
  private String body;
  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  @NotNull
  private UserInfo author;

  private List<String> tags;

}
