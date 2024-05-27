package com.example.moviesearch.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MovieTest {

    @Test
    void testMovieConstructor() {
        Movie movie = new Movie("The Matrix");
        Assertions.assertEquals("The Matrix", movie.getTitle(), "Constructor title should match");
    }

    @Test
    void testMovieSettersAndGetters() {
        Movie movie = new Movie();
        movie.setTitle("The Matrix");
        Assertions.assertEquals("The Matrix", movie.getTitle(), "Setter and Getter for title should work correctly");
    }
}
