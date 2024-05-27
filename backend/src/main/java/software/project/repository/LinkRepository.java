package software.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.project.domain.Links;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Links, Long> {

}
