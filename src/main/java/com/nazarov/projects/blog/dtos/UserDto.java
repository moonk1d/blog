package com.nazarov.projects.blog.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
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
public class UserDto {

  private Long id;
  @NotBlank
  private String name;
  @NotBlank
  private String nickName;

  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
  private LocalDateTime createdDate;

  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
  private LocalDateTime lastModifiedDate;

  private List<BlogPostInfoDto> posts;

}
