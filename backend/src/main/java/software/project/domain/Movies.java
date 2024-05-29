package software.project.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieId")
    private Long movieId;
    private String title;
    private String genres;

    /*@OneToMany(mappedBy = "movies", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ratings> ratings;

    @OneToMany(mappedBy = "movies", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tags> tags;

    @OneToOne(mappedBy = "movies", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Links links;*/



}
