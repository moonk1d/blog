package com.nazarov.projects.blog.dtos;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserDto {

  private Long id;
  @NotBlank
  private String name;
  @NotBlank
  private String nickName;
  private List<BlogPostInfo> posts;

}
