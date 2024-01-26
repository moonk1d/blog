package com.nazarov.projects.blog.models;


import static jakarta.persistence.GenerationType.AUTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "POSTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BlogPost {

  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;
  private String subject;
  private String body;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User author;
  @ManyToMany
  @JoinTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags = new HashSet<>();

  public BlogPost(String subject, String body, User author) {
    this.subject = subject;
    this.body = body;
    this.author = author;
  }

  public BlogPost(String subject, String body, User author, Set<Tag> tags) {
    this.subject = subject;
    this.body = body;
    this.author = author;
    this.tags = tags;
  }

}
