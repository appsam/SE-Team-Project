package software.project.InsertDB;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
public class InsertMovies implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        String csvFile = new File("src/main/java/software/project/dataset/movies.csv").getAbsolutePath(); // CSV 파일 경로
        String tableName = "movies"; // 테이블 이름

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile))) {
            String[] columns = csvReader.readNext();
            if (isTableExists(tableName)) {
                System.out.println("Movies 테이블이 이미 존재합니다.");
                return;
            }
            // 테이블이 없으면 생성
            createTable(tableName);

            // CSV 파일의 데이터를 테이블에 삽입
            String[] data;
            while ((data = csvReader.readNext()) != null) {
                insertData(tableName, data);
            }

            System.out.println("Movies 데이터 삽입 완료");
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    private boolean isTableExists(String tableName) {
        String query = "SELECT 1 FROM " + tableName + " LIMIT 1";
        try {
            jdbcTemplate.queryForObject(query, Integer.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void createTable(String tableName) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "movieId BIGINT," +
                "title VARCHAR(255)," +
                "genres VARCHAR(255)," +
                "averageRating DOUBLE" +
                ")";

        jdbcTemplate.execute(createTableQuery);
        System.out.println("Movies 테이블 생성 완료");
    }

    private void insertData(String tableName, String[] data) {
        String insertQuery = "INSERT INTO " + tableName + " (movieId, title, genres) VALUES (?, ?, ?)";

        jdbcTemplate.update(insertQuery, Long.parseLong(data[0]), data[1], data[2]);
    }
}
