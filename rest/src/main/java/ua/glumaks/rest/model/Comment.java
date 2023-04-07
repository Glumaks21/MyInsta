package ua.glumaks.rest.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @SequenceGenerator(
            name = "comment_id_sequence",
            sequenceName = "comment_id_sequence")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_id_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User author;

    @Column(columnDefinition = "text", nullable = false)
    private String message;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return getId() != null && Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
