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
@ToString
public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieId")
    private Long movieId;
    private String title;
    private String genres;
    private Double averageRating;


    public Movies(String  title){
        this.title = title;
    }

}
