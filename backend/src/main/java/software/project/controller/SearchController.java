package software.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.project.domain.Movies;
import software.project.repository.MemberRepository;
import software.project.repository.MovieRepository;
import software.project.service.MovieService;

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
    public ResponseEntity<String> searchMovies(@RequestParam String title) {
        List<Movies> movies = movieService.searchMovies(title);
        String response = movies.stream()
                .map(movie -> "id: " + movie.getMovieId() + ", title: " + movie.getTitle() + ", description: ")
                .collect(Collectors.joining("\n"));
        return ResponseEntity.ok()
                .header("Content-Type", "text/plain; charset=utf-8")
                .body(response);
    }
    // 자동완성
    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autocompleteMovies(@RequestParam("keyword") String keyword) {
        List<String> titles = movieService.autocompleteMovies(keyword)
                .stream()
                .map(Movies::getTitle)
                .collect(Collectors.toList());
        System.out.println(titles);
        return ResponseEntity.ok(titles);
    }
}