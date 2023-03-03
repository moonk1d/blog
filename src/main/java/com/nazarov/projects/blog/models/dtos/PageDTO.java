package com.nazarov.projects.blog.models.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class PageDTO<T> {

  private List<T> content;
  private int size;
  private int page;
  private int totalPages;
  private long totalElements;

  public static <T> PageDTO<T> create(Page<T> page) {
    return PageDTO
        .<T>builder()
        .content(page.getContent())
        .size(page.getSize())
        .page(page.getNumber())
        .totalPages(page.getTotalPages())
        .totalElements(page.getTotalElements())
        .build();
  }
}
