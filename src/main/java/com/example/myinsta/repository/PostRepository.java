package com.example.myinsta.repository;

import com.example.myinsta.entity.Post;
import com.example.myinsta.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByOwnerOrderByCreationTimeDesc(User user, Pageable pageable);

    Page<Post> findAllByOrderByCreationTimeDesc(Pageable pageable);

}
