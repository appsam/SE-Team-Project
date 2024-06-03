package software.project.InsertDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import software.project.domain.Movies;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PullMovie {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public List<Movies> findData() {
        String sql = "SELECT movieId,title,genres,averageRating FROM movies";
        return jdbcTemplate.query(sql,new MovieMapper());
    }

    public void settingData() {
        String sql = "SELECT COUNT(*) FROM movies WHERE averageRating IS NOT NULL";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        if (count == 0) {
            String updateSql = "UPDATE movies m SET averageRating = (SELECT AVG(rating) FROM ratings WHERE movieId = m.movieId)";
            jdbcTemplate.update(updateSql);
        }
    }

    private static final class MovieMapper implements RowMapper<Movies> {
        @Override
        public Movies mapRow(ResultSet rs, int rowNum) throws SQLException {
            Movies movies = new Movies();
            movies.setMovieId(rs.getLong("movieId"));
            movies.setTitle(rs.getString("title"));
            movies.setGenres(rs.getString("genres"));
            movies.setAverageRating(rs.getDouble("averageRating"));
            return movies;
        }
    }
}
