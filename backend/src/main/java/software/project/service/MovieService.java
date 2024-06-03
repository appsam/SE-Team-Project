package software.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import software.project.domain.Movies;
import software.project.dto.OMDBMovie;
import software.project.dto.OMDBResponse;
import software.project.dto.UserMovieInteraction;
import software.project.repository.MovieRepository;
import software.project.repository.UserMovieInteractionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private final MovieRepository movieRepository;
    @Autowired
    private final UserMovieInteractionRepository userMovieInteractionRepository;

    @Value("${omdb.api.key}")
    private String omdbApiKey;


    @Autowired
    public MovieService(MovieRepository movieRepository, UserMovieInteractionRepository userMovieInteractionRepository) {
        this.movieRepository = movieRepository;
        this.userMovieInteractionRepository = userMovieInteractionRepository;
    }

    public void settingDta(){

    }

    // 추천 기능 추가
    public List<Movies> recommendMovies(Long userId) {
        List<Long> likedMovieIds = userMovieInteractionRepository.findByUserIdAndLikedTrue(userId)
                .stream()
                .map(UserMovieInteraction::getMovieId)
                .collect(Collectors.toList());

        if (likedMovieIds.isEmpty()) {
            return List.of();
        }

        return movieRepository.findAllById(likedMovieIds);
    }

    // OMDB API를 이용한 관련 영화 출력
    public List<Movies> recommendRelatedMovies(String title) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("http://www.omdbapi.com/?s=%s&apikey=%s", title, omdbApiKey);
        OMDBResponse response = restTemplate.getForObject(url, OMDBResponse.class);

        if (response == null || response.getSearch() == null) {
            return List.of();
        }

        return response.getSearch().stream()
                .limit(3)
                .map(this::convertToMovie)
                .collect(Collectors.toList());
    }

    private Movies convertToMovie(OMDBMovie omdbMovie) {
        return new Movies(omdbMovie.getTitle());
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

    /*public List<MovieDTO> getAllMoviesWithAverageRating() {
        return movieRepository.findAllMoviesWithAverageRating();
    }*/
}