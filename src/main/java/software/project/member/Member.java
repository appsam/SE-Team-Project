package software.project.member;

import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@Entity(name = "member")
@Table(name = "member")
public class Member {
    @Id @Column(name = "memberId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //아이디(고유번호)

    @Column(name = "gender", nullable = false) @Enumerated(EnumType.STRING)
    private Gender gender; //성별

    @Column(name = "password", nullable = false)
    private String password; //비밀번호

    @Column(name = "name", nullable = false)
    private String name; //이름

}
