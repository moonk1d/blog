package com.nazarov.projects.blog.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateTagDto {

  @NotBlank(message = "Tag name cannot be empty")
  @Size(min = 2, message = "Tag name is to short. 2 character is min length for tags.")
  private String name;

}
