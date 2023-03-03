package com.nazarov.projects.blog;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.nazarov.projects.blog.controllers.BlogPostController;
import com.nazarov.projects.blog.controllers.TagController;
import com.nazarov.projects.blog.controllers.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

	@Autowired
	BlogPostController blogPostController;

	@Autowired
	TagController tagController;

	@Autowired
	UserController userController;

	@Test
	void contextLoads() {
		assertThat(blogPostController).isNotNull();
		assertThat(tagController).isNotNull();
		assertThat(userController).isNotNull();
	}

}
