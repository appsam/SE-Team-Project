package software.project.controller;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import software.project.member.Gender;
@Getter
@Setter
@ToString
public class MemberForm {

    private Long id; //아이디(고유번호)


    private Gender gender; //성별


    private String password; //비밀번호


    private String name; //이름

}