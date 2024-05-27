package com.example.moviesearch.specification;

import com.example.moviesearch.model.Movie;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class MovieSpecifications {
    // 동적 쿼리
    public static Specification<Movie> withDynamicQuery(String title) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    // 자동완성
    public static Specification<Movie> titleContains(String searchText) {
        return (root, query, cb) -> {
            if (searchText == null || searchText.isEmpty()) {
                return cb.isTrue(cb.literal(true)); // 검색결과 없을시에 모든 결과 반환
            }
            return cb.like(cb.lower(root.get("title")), "%" + searchText.toLowerCase() + "%");
        };
    }
}
