package ua.glumaks.rest.model;

import jakarta.persistence.*;

@Entity
public class Image {

    @Id
    @SequenceGenerator(
            name = "image_id_sequence",
            sequenceName = "image_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "image_id_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    private Long userId;

    private Long postId;

    @Column(nullable = false, length = 64)
    private String url;

}
