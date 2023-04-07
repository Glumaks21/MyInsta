package ua.glumaks.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.glumaks.rest.model.Image;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByUserId(Long userId);

    Optional<Image> findByPostId(Long postId);


}
