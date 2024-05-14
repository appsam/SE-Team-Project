package com.example.moviesearch.repository;

import com.example.moviesearch.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    List<Movie> findByTitleContaining(String title);
    // JpaSpecificationExecutor<Movie>를 상속받아 동적 쿼리를 사용할 수 있게 함
}
