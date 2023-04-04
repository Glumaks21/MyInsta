package com.example.myinsta.repository;

import com.example.myinsta.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByUserId(Long userId);

    Optional<Image> findByPostId(Long postId);


}
