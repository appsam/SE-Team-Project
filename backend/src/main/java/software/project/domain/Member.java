package software.project.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @ElementCollection
    @CollectionTable(name = "member_genres", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "genre")
    private List<String> preferredGenres = new ArrayList<>();
}
