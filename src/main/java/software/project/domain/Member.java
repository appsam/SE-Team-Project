package software.project.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long memberId; //아이디(고유번호)

    @Column(name="login_id", nullable = false)
    private String loginId; //로그인 아이디
    private String password; //비밀번호
    private String name; //이름

}
