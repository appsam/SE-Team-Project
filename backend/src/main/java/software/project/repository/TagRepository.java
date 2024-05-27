package software.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.project.domain.TagId;
import software.project.domain.Tags;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tags, TagId> {
    List<Tags> findByUserId(Long userId);
    List<Tags> findByMovieId(Long movieId);
}