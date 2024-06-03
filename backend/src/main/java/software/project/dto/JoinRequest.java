package software.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.project.domain.Member;
import software.project.domain.MemberRole;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

    @NotBlank(message = "ID를 입력하세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
    private String passwordCheck;

    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    private List<String> selectedGenres;

    /*public Member toEntity(){
        return Member.builder()
                .loginId(this.loginId)
                .password(this.password)
                .name(this.name)
                .role(MemberRole.USER)
                .selectedGenres(this.selectedGenres)
                .build();
    }*/
}