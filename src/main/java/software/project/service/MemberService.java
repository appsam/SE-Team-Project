package software.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.project.dto.JoinRequest;
import software.project.dto.LoginRequest;
import software.project.domain.Member;
import software.project.repository.MemberRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository; // = new MemoryMemberRepository();

    /**
     * loginId 중복체크
     * 회원가입 기능 구현 시 사용
     * 중복되면 true return
     */
    public boolean checkLoginIdDuplicate(String loginId){
        return memberRepository.existsByLoginId(loginId);
    }


    /**
     * 회원가입 기능
     * 화면에서 데이터들 입력받아 Member로 변환 후 저장
     * 중복 체크는 Contoller에서
     */
    public void join(JoinRequest req){
        memberRepository.save(req.toEntity());
    }


    /**
     * 로그인 기능
     * 로그인 화면에서 아이디 및 비밀번호를 입력받아 일치하면 멤버 반환
     * 존재하지 않거나 비밀번호 틀리면 null 반환
     */
    public Member login(LoginRequest req){
        Optional<Member> optionalMember = memberRepository.findByLoginId(req.getLoginId());

        if(optionalMember.isEmpty()) return null;

        Member member = optionalMember.get();

        if(!member.getPassword().equals(req.getPassword())) return null;

        return member;
    }


    /**
     * id(Long)를 입력받아 멤버를 반환 해주는 기능
     * 인증 시 사용
     * id로 찾아온 멤버가 존재하면 멤버 반환
     */
    public Member getLoginMemberById(Long memberId) {
        if(memberId == null) return null;

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if(optionalMember.isEmpty()) return null;

        return optionalMember.get();
    }


    /**
     * loginId(String)를 입력받아 멤버를 반환 해주는 기능
     * 인증 시 사용
     * loginId로 찾아온 멤버가 존재하면 멤버 반환
     */
    public Member getLoginMemberByLoginId(String loginId) {
        if(loginId == null) return null;

        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        if(optionalMember.isEmpty()) return null;

        return optionalMember.get();
    }

}
