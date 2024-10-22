package com.nazarov.projects.blog.models.mappers;

import com.nazarov.projects.blog.dtos.BlogPostDto;
import com.nazarov.projects.blog.dtos.BlogPostInfoDto;
import com.nazarov.projects.blog.dtos.CreateBlogPostDto;
import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.Tag;
import com.nazarov.projects.blog.models.User;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogPostEntityMapper {

  private final ModelMapper mapper;

  public BlogPostDto toDto(BlogPost blogPost) {
    return mapper
        .map(blogPost, BlogPostDto.class)
        .toBuilder()
        .tags(blogPost
            .getTags()
            .stream()
            .map(Tag::getName)
            .collect(Collectors.toList()))
        .build();
  }

  public BlogPostInfoDto toInfo(BlogPost blogPost) {
    return mapper
        .map(blogPost, BlogPostInfoDto.class)
        .toBuilder()
        .tags(blogPost
            .getTags()
            .stream()
            .map(Tag::getName)
            .collect(Collectors.toList()))
        .build();
  }

  public BlogPost toEntity(BlogPostDto blogPostDto) {
    return mapper.map(blogPostDto, BlogPost.class);
  }

  public BlogPost toEntity(CreateBlogPostDto createBlogPostDto, User user, Set<Tag> tags) {
    return mapper.map(createBlogPostDto, BlogPost.class).toBuilder()
        .author(user).tags(tags).build();
  }

}
