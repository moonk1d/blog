package com.nazarov.projects.blog.models;

import static javax.persistence.GenerationType.AUTO;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {

  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;
  private String name;
  private String nickName;

  @OneToMany(mappedBy = "author")
  private Set<BlogPost> posts = new HashSet<>();

  public User(String name, String nickName) {
    this.name = name;
    this.nickName = nickName;
  }

  @PreRemove
  private void removeUserFromPosts() {
    for (BlogPost p : posts) {
      p.setAuthor(null);
    }
  }
}
