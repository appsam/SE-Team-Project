package com.example.moviesearch.service;

import com.example.moviesearch.model.Movie;
import com.example.moviesearch.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @MockBean
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        Movie movie1 = new Movie("The Matrix");
        Movie movie2 = new Movie("The Matrix Reloaded");
        Movie movie3 = new Movie("The Matrix Revolutions");

        given(movieRepository.findByTitleContaining("Matrix")).willReturn(Arrays.asList(movie1, movie2, movie3));
    }

    @Test
    void searchMoviesReturnsResults() {
        List<Movie> results = movieService.searchMovies("Matrix");
        assertThat(results).hasSize(3);
        assertThat(results.get(0).getTitle()).isEqualTo("The Matrix");
        assertThat(results.get(1).getTitle()).isEqualTo("The Matrix Reloaded");
        assertThat(results.get(2).getTitle()).isEqualTo("The Matrix Revolutions");
    }
}
