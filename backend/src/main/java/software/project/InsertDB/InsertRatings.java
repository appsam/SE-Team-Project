package software.project.InsertDB;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.File;

@Component
public class InsertRatings implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InsertRatings(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        String csvFile = new File("src/main/java/software/project/dataset/ratings.csv").getAbsolutePath(); // CSV 파일 경로
        String tableName = "ratings"; // 테이블 이름

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile))) {
            String[] columns = csvReader.readNext();
            if (columns == null) {
                System.err.println("CSV 파일이 비어 있습니다.");
                return;
            }
            // 테이블이 이미 존재하는지 확인
            if (isTableExists(tableName)) {
                System.out.println("Ratings 테이블이 이미 존재합니다.");
                return;
            }
            // 테이블이 없으면 생성
            createTable(tableName);

            // CSV 파일의 데이터를 테이블에 삽입
            String[] data;
            while ((data = csvReader.readNext()) != null) {
                insertData(tableName, data);
            }

            System.out.println("Ratings 데이터 삽입 완료");

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
                "userId BIGINT," +
                "movieId BIGINT," +
                "rating DOUBLE," +
                "timestamp BIGINT" +
                ")";
        jdbcTemplate.execute(createTableQuery);
        System.out.println("Ratings 테이블 생성 완료");
    }

    private void insertData(String tableName, String[] data) {
        if (data.length != 4) {
            System.err.println("잘못된 데이터 형식입니다: " + String.join(",", data));
            return;
        }
        String insertQuery = "INSERT INTO " + tableName + " (userId, movieId, rating, timestamp) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertQuery, Long.parseLong(data[0]), Long.parseLong(data[1]),
                Double.parseDouble(data[2]), Long.parseLong(data[3]));
    }
}
