package com.nazarov.projects.blog.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateBlogPostDto {

  @NotBlank(message = "Subject cannot be empty")
  @Size(max = 100, message = "Subject cannot exceed 100 characters")
  private String subject;

  @NotBlank(message = "Body cannot be empty")
  @Size(max = 1500, message = "Body cannot exceed 1500 characters")
  private String body;

  @NotNull(message = "Author is required")
  private UserInfoDto author;

  private List<String> tags;

}
