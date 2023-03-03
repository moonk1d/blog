package com.nazarov.projects.blog.repositories;

import com.nazarov.projects.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
