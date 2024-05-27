package software.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import software.project.domain.Movies;
import software.project.domain.Tags;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movies, Long>, JpaSpecificationExecutor<Movies> {
    List<Movies> findByTitleContaining(String title);
    // JpaSpecificationExecutor<Movie>를 상속받아 동적 쿼리를 사용할 수 있게 함

    /*@Query("SELECT m FROM Movies m WHERE m.title LIKE %:keyword%")
    List<Movies> findByTitleContaining(String keyword);*/
    Movies findByMovieId(Long id);
}
