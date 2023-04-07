package ua.glumaks.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UploadFile {

    @Id
    @SequenceGenerator(
            name = "upload_file_id",
            sequenceName = "upload_file_id")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "upload_file_id")
    private Long id;

    private String originalName;

    @Column(nullable = false)
    private String storageName;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long size;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModified;

}
