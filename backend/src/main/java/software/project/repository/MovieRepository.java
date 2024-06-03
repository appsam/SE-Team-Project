package software.project.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import software.project.domain.Movies;
import software.project.domain.Tags;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movies, Long>, JpaSpecificationExecutor<Movies> {
    List<Movies> findByTitleContaining(String title);

    Movies findByMovieId(Long id);

    Movies findByTitle(String title);
    @Query("SELECT m FROM Movies m WHERE m.title = :title")
    Long findIdByTitle(@Param("title") String title);

    @Query("SELECT m, AVG(r.rating) AS averageRating " +
            "FROM Movies m " +
            "JOIN Ratings r ON m.movieId = r.movieId " +
            "GROUP BY m " +
            "ORDER BY averageRating DESC")
    List<Object[]> findMoviesWithAverageRating();







}
