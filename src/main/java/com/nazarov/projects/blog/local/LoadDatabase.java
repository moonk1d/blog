package com.nazarov.projects.blog.local;

import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.Tag;
import com.nazarov.projects.blog.models.User;
import com.nazarov.projects.blog.repositories.BlogPostRepository;
import com.nazarov.projects.blog.repositories.TagRepository;
import com.nazarov.projects.blog.repositories.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Profile("local")
@Slf4j
class LoadDatabase {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BlogPostRepository blogPostRepository;

  @Autowired
  private TagRepository tagRepository;


  @Bean
  CommandLineRunner initDatabase() {

    return args -> {
      log.info("Preloading tags: ");
      Tag tag1 = new Tag("story");
      Tag tag2 = new Tag("book");
      Tag tag3 = new Tag("adventure");
      Tag tag4 = new Tag("new");

      List
          .of(tag1, tag2, tag3, tag4)
          .forEach(tag -> log.info("Tag: " + tagRepository.save(tag)));

      log.info("Preloading users: ");

      User user1 = new User("Bilbo Baggins", "burglar");
      User user2 = new User("Frodo Baggins", "thief");
      User user3 = new User("Diamond Took", "took");
      User user4 = new User("Fredegar Bolger", "Bolger");
      User user5 = new User("Old Noakes", "old");

      List
          .of(user1, user2, user3, user4, user5)
          .forEach(user -> log.info("User: " + userRepository.save(user)));

      log.info("Preloading posts: ");
      BlogPost post1 = new BlogPost("Bilbo story", "Hobbits story", user1);
      BlogPost post2 = new BlogPost("Bilbo story", "The ring story", user1);
      BlogPost post3 = new BlogPost("Frodo story", "The ring story", user2);
      BlogPost post4 = new BlogPost("Took story", "Hobbits story", user3);
      BlogPost post5 = new BlogPost("Bolger story", "Hobbits story", user4);
      BlogPost post6 = new BlogPost("Noakes story", "The ring story", user5);

      List
          .of(post1, post2, post3, post4, post5, post6)
          .forEach(post -> log.info("Post: " + blogPostRepository.save(post)));
    };
  }
}
