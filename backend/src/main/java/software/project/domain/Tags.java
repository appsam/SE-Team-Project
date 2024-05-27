package software.project.domain;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(TagId.class)
public class Tags {
    @Id
    @Column(name = "userId")
    private Long userId;
    @Id
    @Column(name = "movieId")
    private Long movieId;

    @Id
    private String tag;

    @Column(name = "timeStamp")
    private Long timeStamp;

    @ManyToOne
    @JoinColumn(name = "movieId", insertable = false, updatable = false)
    private Movies movies;
}
