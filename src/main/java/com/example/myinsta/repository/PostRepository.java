package com.example.myinsta.repository;

import com.example.myinsta.entity.Post;
import com.example.myinsta.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOwnerOrderByCreationTimeDesc(User user);

    List<Post> findAllByOrderByCreationTimeDesc();

}
