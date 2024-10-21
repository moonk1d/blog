package com.nazarov.projects.blog.models;

import static jakarta.persistence.GenerationType.AUTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
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
  // TODO add creation date/timestamp

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
  private Set<BlogPost> posts = new HashSet<>();

  public User(String name, String nickName) {
    this.name = name;
    this.nickName = nickName;
  }

}
