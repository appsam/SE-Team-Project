package software.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import software.project.domain.Movies;
import software.project.repository.MovieRepository;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // 동적 쿼리로 영화 검색
    public List<Movies> searchMovies(String title) {
        return movieRepository.findAll(MovieSpecifications.withDynamicQuery(title));
    }

    // 자동완성 기능
    public List<Movies> autocompleteMovies(String keyword) {
        // PageRequest.of(0, 5)을 사용하여 결과의 최대 개수를 5개로 제한
        return movieRepository.findAll(MovieSpecifications.titleContains(keyword), PageRequest.of(0, 5)).getContent();
    }

    /*public List<Movies> autocompleteMovies(String keyword) {
        return movieRepository.findByTitleContaining(keyword);
    }*/
}