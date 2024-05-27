package software.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.project.domain.RatingId;
import software.project.domain.Ratings;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Ratings, RatingId> {
    List<Ratings> findByUserId(Long userId);
    List<Ratings> findByMovieId(Long movieId);
}
