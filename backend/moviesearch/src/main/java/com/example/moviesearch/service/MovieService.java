package com.example.moviesearch.service;

import com.example.moviesearch.model.Movie;
import com.example.moviesearch.repository.MovieRepository;
import com.example.moviesearch.specification.MovieSpecifications;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // 동적 쿼리로 영화 검색
    public List<Movie> searchMovies(String title) {
        return movieRepository.findAll(MovieSpecifications.withDynamicQuery(title));
    }

    // 자동완성 기능
    public List<Movie> autocompleteMovies(String keyword) {
        // PageRequest.of(0, 5)을 사용하여 결과의 최대 개수를 5개로 제한
        return movieRepository.findAll(MovieSpecifications.titleContains(keyword), PageRequest.of(0, 5)).getContent();
    }
}
