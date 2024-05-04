package software.project.service;

import software.project.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    public void join(Member member);

    public Optional<Member> findMember(Long memberId);
    public List<Member> findAllMembers();

}
