package software.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import software.project.dto.UserMovieInteraction;

import java.util.List;

public interface UserMovieInteractionRepository extends JpaRepository<UserMovieInteraction, Long> {
    List<UserMovieInteraction> findByUserIdAndLikedTrue(Long userId);
}
