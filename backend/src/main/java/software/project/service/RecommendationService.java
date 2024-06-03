package software.project.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.project.controller.MovieController;
import software.project.domain.Member;
import software.project.domain.Movies;
import software.project.domain.Ratings;
import software.project.repository.MemberRepository;
import software.project.repository.MovieRepository;
import software.project.repository.RatingRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MemberRepository memberRepository;

    public List<Movies> recommendMovies(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("User not found"));
        List<String> preferredGenres = member.getPreferredGenres();
        if (preferredGenres == null) {
            preferredGenres = Collections.emptyList();
        }
        List<Movies> allMovies = movieRepository.findAll();

        Map<Movies, Double> movieScores = new HashMap<>();

        for (Movies movie : allMovies) {
            String genres = movie.getGenres();
            if (genres == null) {
                genres = "";
            }
            List<String> movieGenres = Arrays.asList(movie.getGenres().split("\\|"));
            double score = calculateCosineSimilarity(preferredGenres, movieGenres);

            movieScores.put(movie, score);
        }
        List<Movies> sortedMovies = movieScores.entrySet()
                .stream()
                .sorted(Map.Entry.<Movies, Double>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        logger.info("Top 10 recommended movies: {}", sortedMovies);

        return movieScores.entrySet()
                .stream()
                .sorted(Map.Entry.<Movies, Double>comparingByValue().reversed())
                .limit(4)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private double calculateCosineSimilarity(List<String> genres1, List<String> genres2) {
        if (genres1 == null) {
            genres1 = Collections.emptyList();
        }
        if (genres2 == null) {
            genres2 = Collections.emptyList();
        }

        Set<String> allGenres = new HashSet<>();
        allGenres.addAll(genres1);
        allGenres.addAll(genres2);

        int[] vector1 = new int[allGenres.size()];
        int[] vector2 = new int[allGenres.size()];

        int index = 0;
        for (String genre : allGenres) {
            vector1[index] = genres1.contains(genre) ? 1 : 0;
            vector2[index] = genres2.contains(genre) ? 1 : 0;
            index++;
        }

        return cosineSimilarity(vector1, vector2);
    }

    private double cosineSimilarity(int[] vector1, int[] vector2) {
        int dotProduct = 0;
        int norm1 = 0;
        int norm2 = 0;

        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            norm1 += vector1[i] * vector1[i];
            norm2 += vector2[i] * vector2[i];
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}