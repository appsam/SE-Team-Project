package software.project;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.project.repository.JpaMemberRepository;
import software.project.repository.MemberRepository;
import software.project.repository.MemoryMemberRepository;
import software.project.service.MemberService;
import software.project.service.MemberServiceImpl;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    private final DataSource dataSource;
    private final EntityManager em;
    public AppConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;this.em = em;
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JpaMemberRepository(em);
    }

}
