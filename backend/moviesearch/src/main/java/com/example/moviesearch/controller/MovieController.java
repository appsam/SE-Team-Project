package com.example.moviesearch.controller;

import com.example.moviesearch.model.Movie;
import com.example.moviesearch.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/search")
    public ResponseEntity<String> searchMovies(@RequestParam String title) {
        List<Movie> movies = movieService.searchMovies(title);
        String response = movies.stream()
                .map(movie -> "id: " + movie.getId() + ", title: " + movie.getTitle())
                .collect(Collectors.joining("\n"));
        return ResponseEntity.ok()
                .header("Content-Type", "text/plain; charset=utf-8")
                .body(response);
    }

    // 자동완성
    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autocompleteMovies(@RequestParam String keyword) {
        List<String> titles = movieService.autocompleteMovies(keyword)
                .stream()
                .map(Movie::getTitle)
                .collect(Collectors.toList());
        return ResponseEntity.ok(titles);
    }
}
