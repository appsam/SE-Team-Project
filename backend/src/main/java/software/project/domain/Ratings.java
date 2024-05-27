package software.project.domain;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(RatingId.class)
public class Ratings {
    @Id
    @Column(name="userId")
    private Long userId;
    @Id
    @Column(name="movieId")
    private Long movieId;

    @Column
    private double rating;
    @Column(name = "timeStamp")
    private Long timeStamp;

    @ManyToOne
    @JoinColumn(name = "movieId", insertable = false, updatable = false)
    private Movies movies;
}

