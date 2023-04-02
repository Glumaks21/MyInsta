package com.example.myinsta.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
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

    private String location;

    private Integer likes;

    @ManyToMany
    @ToString.Exclude
    private Set<User> likedUsers;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User owner;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "post"
    )
    @ToString.Exclude
    private Set<Comment> comments;

    @Timestamp
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
