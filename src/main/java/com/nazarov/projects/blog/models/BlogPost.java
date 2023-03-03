package com.nazarov.projects.blog.models;

import static javax.persistence.GenerationType.AUTO;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
