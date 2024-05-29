package software.project.domain;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Links {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieId")
    private Long movieId;

    @Column(name = "imdbId")
    private Long imdbId;
    @Column(name = "tmdbId")
    private Long tmdbId;

    /*@OneToOne
    @JoinColumn(name = "movieId", insertable = false, updatable = false)
    private Movies movies;*/
}
