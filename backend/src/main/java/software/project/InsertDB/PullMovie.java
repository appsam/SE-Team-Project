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
        String sql = "SELECT movieId,title,genres FROM movies";
        return jdbcTemplate.query(sql,new MovieMapper());
    }

    private static final class MovieMapper implements RowMapper<Movies> {
        @Override
        public Movies mapRow(ResultSet rs, int rowNum) throws SQLException {
            Movies movies = new Movies();
            movies.setMovieId(rs.getLong("movieId"));
            movies.setTitle(rs.getString("title"));
            movies.setGenres(rs.getString("genres"));
            return movies;
        }
    }
}
