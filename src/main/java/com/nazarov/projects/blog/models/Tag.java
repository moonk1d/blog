package com.nazarov.projects.blog.models;

import static jakarta.persistence.GenerationType.AUTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TAGS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Tag {

  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;
  @Column(unique = true)
  private String name;

  @ManyToMany(mappedBy = "tags")
  private Set<BlogPost> posts = new HashSet<>();

  public Tag(String name) {
    this.name = name;
  }

  @PreRemove
  private void removeTagsFromPosts() {
    for (BlogPost p : posts) {
      p.getTags().remove(this);
    }
  }
}
