package software.project.repository;

import software.project.member.Member;

import java.util.*;

public interface MemberRepository {
    public Member save(Member member);


    public Optional<Member> findById(Long id);

    public Optional<Member> findByName(String name);

    public List<Member> findAll();
    public void clearStore();
}
