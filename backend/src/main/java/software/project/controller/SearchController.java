package software.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.project.domain.Movies;
import software.project.repository.MemberRepository;
import software.project.repository.MovieRepository;
import software.project.service.MovieService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SearchController {

    private MovieRepository movieRepository;
    @Autowired
    private MovieService movieService;




    @GetMapping("/search")
    public ResponseEntity<List<Movies>> searchMovies(@RequestParam String title) {
        List<Movies> movies = movieService.searchMovies(title);
        List<Movies> recommendedMovies = movieService.recommendRelatedMovies(title);

        // 영화 및 추천 영화 목록을 한 리스트에 합침
        //List<Movies> allMovies = new ArrayList<>();
        //allMovies.addAll(movies);
        //allMovies.add((Movies) recommendedMovies);

        return ResponseEntity.ok(recommendedMovies);
    }
    // 자동완성
    /*@GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autocompleteMovies(@RequestParam("keyword") String keyword) {
        List<String> titles = movieService.autocompleteMovies(keyword)
                .stream()
                .map(Movies::getTitle)
                .collect(Collectors.toList());
        System.out.println(titles);
        return ResponseEntity.ok(titles);
    }*/

    @GetMapping("/autocomplete")
    public ResponseEntity<List<MovieDTO>> autocompleteMovies(@RequestParam("keyword") String keyword) {
        List<MovieDTO> movieDTOs = movieService.autocompleteMovies(keyword)
                .stream()
                .map(movie -> new MovieDTO(movie.getMovieId(), movie.getTitle()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(movieDTOs);
    }

    // MovieDTO 클래스 정의
    public static class MovieDTO {
        private Long movieId;
        private String title;

        public MovieDTO(Long movieId, String title) {
            this.movieId = movieId;
            this.title = title;
        }

        public Long getMovieId() {
            return movieId;
        }

        public String getTitle() {
            return title;
        }
    }
}
