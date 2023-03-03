package com.nazarov.projects.blog.models;

import static javax.persistence.GenerationType.AUTO;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
