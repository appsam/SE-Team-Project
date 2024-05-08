package software.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import software.project.domain.Member;

import java.util.*;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByLoginId(String loginId);
    Optional<Member> findByLoginId(String loginId);

}
