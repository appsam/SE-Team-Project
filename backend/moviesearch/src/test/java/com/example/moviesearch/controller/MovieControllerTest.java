package com.example.moviesearch.controller;

import com.example.moviesearch.service.MovieService;
import com.example.moviesearch.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Movie movie1 = new Movie("The Matrix");
        Movie movie2 = new Movie("The Matrix Reloaded");
        Movie movie3 = new Movie("The Matrix Revolutions");

        List<Movie> movies = Arrays.asList(movie1, movie2, movie3);
        given(movieService.searchMovies("Matrix")).willReturn(movies);
    }

    @Test
    void searchMoviesReturnsResults() throws Exception {
        mockMvc.perform(get("/movies/search?title=Matrix")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'title':'The Matrix'},{'title':'The Matrix Reloaded'},{'title':'The Matrix Revolutions'}]".replace('\'', '"')));
    }
}