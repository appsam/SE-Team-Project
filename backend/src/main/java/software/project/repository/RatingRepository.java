package software.project.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import software.project.domain.RatingId;
import software.project.domain.Ratings;


import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Ratings, RatingId> {
    List<Ratings> findByUserId(Long userId);
    List<Ratings> findByMovieId(Long movieId);

    @Query("SELECT r.movieId FROM Ratings r WHERE rating = 5.0 GROUP BY r.movieId ORDER BY COUNT(r.rating) DESC")
    List<Long> findTop4MovieId(Pageable pageable);

}
