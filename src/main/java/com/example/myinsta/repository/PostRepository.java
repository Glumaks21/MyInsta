package com.example.myinsta.repository;

import com.example.myinsta.model.Post;
import com.example.myinsta.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByAuthor(User user, Pageable pageable);
    Page<Post> findAllByAuthor_Id(Long userId, Pageable pageable);

    Page<Post> findAllByOrderByCreationTimeDesc(Pageable pageable);

    @Query("SELECT COUNT(lu) FROM Post p JOIN p.likedUsers lu WHERE p.id = :id")
    Integer countLikesById(@Param("id") Long id);

}
