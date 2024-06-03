package software.project.controller;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.project.InsertDB.PullMovie;
import software.project.domain.Movies;
import software.project.domain.Ratings;
import software.project.domain.Tags;
import software.project.repository.MovieRepository;
import software.project.repository.RatingRepository;
import software.project.repository.TagRepository;
import software.project.service.GetMoviePoster;
import software.project.service.MovieService;
import software.project.service.RecommendationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final PullMovie pullMovie;
    private final MovieService movieService;
    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;
    private final TagRepository tagRepository;
    private final GetMoviePoster moviePoster;
    private final RecommendationService recommendationService;

    @Autowired
    public MovieController(PullMovie pullMovie, MovieService movieService, MovieRepository movieRepository,
                           RatingRepository ratingRepository, TagRepository tagRepository, GetMoviePoster moviePoster,RecommendationService recommendationService) {
        this.pullMovie = pullMovie;
        this.movieService = movieService;
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.tagRepository = tagRepository;
        this.moviePoster = moviePoster;
        this.recommendationService = recommendationService;
    }

    @GetMapping("/setting")
    public void settingData(){
        pullMovie.settingData();
    }


    @GetMapping("/recommend/{memberId}")
    public List<Map<String, Object>> getRecommendations(@PathVariable("memberId") Long memberId) throws JSONException {
        List<Map<String, Object>> top4PosterUrlWithIds = new ArrayList<>();
        List<Movies> recommendMovieName4 = recommendationService.recommendMovies(memberId);

        for (Movies movies : recommendMovieName4) {
            if(movies != null) {
                String posterUrl = moviePoster.getPoster(movies.getTitle());
                Long movieId = movies.getMovieId();

                // 포스터가 없을 경우 무작위로 영화 검색
                while(posterUrl == null) {
                    Long randomNumber = (long)(Math.random() * 9742) + 1;
                    Movies randomMovie = movieRepository.findByMovieId(randomNumber);
                    if (randomMovie != null) {  // null 체크
                        posterUrl = moviePoster.getPoster(randomMovie.getTitle());
                        if (posterUrl != null) {  // 포스터가 있을 경우 movieId를 변경
                            movieId = randomMovie.getMovieId();
                        }
                    }
                }

                Map<String, Object> posterWithId = Map.of(
                        "poster", posterUrl,
                        "movieId", movieId
                );
                top4PosterUrlWithIds.add(posterWithId);
            }
        }
        return top4PosterUrlWithIds;
    }

    @GetMapping("/movies")
    public List<Movies> allMovies() {
        return pullMovie.findData();
    }

    @GetMapping("/rating4")
    public List<Map<String, Object>> top4Movies() throws JSONException {
        List<Long> top4MovieId = ratingRepository.findTop4MovieId(PageRequest.of(0, 4));
        List<Map<String, Object>> top4PosterUrlWithIds = new ArrayList<>();

        for (Long movieId : top4MovieId) {
            Movies movie = movieRepository.findByMovieId(movieId);
            if (movie != null) {
                String posterUrl = moviePoster.getPoster(movie.getTitle());
                Map<String, Object> posterWithId = Map.of(
                        "poster", posterUrl,
                        "movieId", movieId
                );
                top4PosterUrlWithIds.add(posterWithId);
            }
        }
        return top4PosterUrlWithIds;
    }



    @GetMapping("/{id}")
    public Map<String, Object> getMovieDetails(@PathVariable("id") Long id) {
        Movies movie = movieRepository.findByMovieId(id);
        Double averageRating = ratingRepository.findByMovieId(id)
                .stream().collect(Collectors.averagingDouble(Ratings::getRating));
        List<String> tags = tagRepository.findByMovieId(id)
                .stream().map(Tags::getTag).collect(Collectors.toList());

        String posterUrl = null;

        try {
            posterUrl = moviePoster.getPoster(movie.getTitle());
        } catch (JSONException e) {
            logger.error("Error fetching poster for movie: {}", movie.getTitle(), e);
        }

        // null 값을 허용하지 않도록 체크하고 설정
        Map<String, Object> movieDetails = Map.of(
                "title", movie.getTitle() != null ? movie.getTitle() : "",
                "genres", movie.getGenres() != null ? movie.getGenres() : "",
                "poster", posterUrl != null ? posterUrl : "default_poster_url",
                "averageRating", averageRating != null ? averageRating : 0.0,
                "tags", tags != null ? tags : List.of()
        );

        return movieDetails;
    }

}