package com.nazarov.projects.blog.local;

import com.nazarov.projects.blog.models.BlogPost;
import com.nazarov.projects.blog.models.Tag;
import com.nazarov.projects.blog.models.User;
import com.nazarov.projects.blog.repositories.BlogPostRepository;
import com.nazarov.projects.blog.repositories.TagRepository;
import com.nazarov.projects.blog.repositories.UserRepository;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
      User user2 = new User("Frodo Baggins", "ringbearer");
      User user3 = new User("Samwise Gamgee", "gardener");
      User user4 = new User("Gandalf the Grey", "wizard");
      User user5 = new User("Gollum", "ringtraker");

      List
          .of(user1, user2, user3, user4, user5)
          .forEach(user -> log.info("User: " + userRepository.save(user)));

      log.info("Preloading posts: ");
      BlogPost post1 = new BlogPost("How I Found the Ring", "Once upon a time, I found a shiny ring in a dark cave. You won't believe what happened next!", user1, Set.of(tag1, tag2));
      BlogPost post2 = new BlogPost("Life in the Shire", "Living in the Shire isn't that boring as it might seem, especially when there's second breakfast to look forward to!", user1, Set.of(tag3, tag2));
      BlogPost post3 = new BlogPost("My Journey with the Ring", "Carrying the ring isn't easy, but I am determined to do my duty. Wish me luck!", user2, Set.of(tag1));
      BlogPost post4 = new BlogPost("Fear and Courage in Mt. Doom", "Facing down Gollum was scary, but I held on and survived the ordeal. Here's how it went.", user2, Set.of(tag2));
      BlogPost post5 = new BlogPost("Gardening Tips From a Hobbit", "Gardening is a noble profession. Here are a few tips from a seasoned gardener - me!", user3, Set.of(tag4));
      BlogPost post6 = new BlogPost("Standing up to a Spider", "With sheer grit and a little luck, I tackled a terrifying giant spider. Here's my scary adventure!", user3, Set.of(tag1));
      BlogPost post7 = new BlogPost("Wizard's Tales", "Being a wizard isn't all magic and fireworks. It's about wisdom and courage too. Let me share my story with you.", user4);
      BlogPost post8 = new BlogPost("Showdown with the Balrog", "In a duel for the ages, I faced the mighty Balrog. The victory was hard-won, but worth it.", user4);
      BlogPost post9 = new BlogPost("Life after the Ring", "I thought getting the ring would end my troubles. I couldn't be more wrong. It's quite the tale!", user5);
      BlogPost post10 = new BlogPost("My Precious", "Oh, how I miss my precious! The ring, my precious, was everything to me. My joy, my agony.", user5);
      BlogPost post11 = new BlogPost("Bilbo's Birthday Party", "My eleventy-first birthday party was quite the event! Learn what led up to my grand exit", user1);
      BlogPost post12 = new BlogPost("The Power of Friendship", "During our journey to Mordor, I learned the true power of friendship. Let me share my story.", user2);
      BlogPost post13 = new BlogPost("A Close Encounter with Orcs", "I had a close brush with orcs and lived to tell the tale. Here's what happened.", user3);
      BlogPost post14 = new BlogPost("Rescuing the Hobbits from Isengard", "Remembering the day I helped rescue the hobbits from the tower of Orthanc in Isengard", user4);
      BlogPost post15 = new BlogPost("The Fall of Barad-dur", "From the joy of ring's destruction to the fall of the Dark Tower. Here's my epic journey in brief.", user5);

      List
          .of(post1, post2, post3, post4, post5, post6, post7, post8, post9, post10, post11, post12, post13, post14, post15)
          .forEach(post -> log.info("Post: " + blogPostRepository.save(post)));
    };
  }
}
