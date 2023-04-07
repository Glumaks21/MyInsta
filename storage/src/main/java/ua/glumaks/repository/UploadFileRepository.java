package ua.glumaks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.glumaks.model.UploadFile;

import java.util.Optional;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    Optional<UploadFile> findByStorageName(String name);
}

