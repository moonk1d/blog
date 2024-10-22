package com.nazarov.projects.blog.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserInfoDto {

  private Long id;
  private String name;
  private String nickName;

  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
  private LocalDateTime createdDate;

  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
  private LocalDateTime lastModifiedDate;

}
