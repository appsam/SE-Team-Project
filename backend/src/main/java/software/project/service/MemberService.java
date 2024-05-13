package software.project.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.project.config.SecurityUtil;
import software.project.domain.Member;
import software.project.dto.JoinRequest;
import software.project.dto.LoginRequest;
import software.project.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    // 아이디 중복
    public boolean checkLoginIdDuplicate(String loginId){
        return memberRepository.existsByLoginId(loginId);
    }



    // 회원강비(db에 저장)
    public void join(JoinRequest joinRequest) {
        memberRepository.save(joinRequest.toEntity());
    }



    // 로그인
    public Member login(LoginRequest loginRequest) {
        Member findMember = memberRepository.findByLoginId(loginRequest.getLoginId());

        if(findMember == null){
            return null;
        }

        if (!findMember.getPassword().equals(loginRequest.getPassword())) {
            return null;
        }

        return findMember;
    }

    public Member getLoginMemberByLoginId(String loginId){
        if(loginId == null) return null;

        Optional<Member> findMember = Optional.ofNullable(memberRepository.findByLoginId(loginId));
        return findMember.orElse(null);

    }
}
