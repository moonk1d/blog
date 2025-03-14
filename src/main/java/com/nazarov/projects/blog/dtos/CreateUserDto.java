package com.nazarov.projects.blog.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateUserDto {

  @NotBlank(message = "User name cannot be empty")
  private String name;

  @NotBlank(message = "User nickname cannot be empty")
  private String nickName;

}
