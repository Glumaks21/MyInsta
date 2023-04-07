package ua.glumaks.rest.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Post {

    @Id
    @SequenceGenerator(
            name = "post_id_sequence",
            sequenceName = "user_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_id_sequence"
    )
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(columnDefinition = "text", nullable = false, length = 4096)
    private String body;

    @Column(length = 255)
    private String location;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Image> images;

    @ManyToMany
    @ToString.Exclude
    private Set<User> likedUsers;

    @ManyToOne
    @JoinColumn(updatable = false, nullable = false)
    private User author;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "post"
    )
    @ToString.Exclude
    private Set<Comment> comments = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationTime;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return getId() != null && Objects.equals(getId(), post.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
