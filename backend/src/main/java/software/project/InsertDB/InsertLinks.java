package software.project.InsertDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Component
public class InsertLinks implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InsertLinks(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;}


    @Override
    public void run(String... args) throws Exception {
        String csvFile = new File("src/main/java/software/project/dataset/links.csv").getAbsolutePath(); //"C:\\dataSet\\links.csv"; // CSV 파일 경로
        String tableName = "links"; // 테이블 이름

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String headerLine = br.readLine();
            String[] columns = headerLine.split(",");
            // 테이블이 이미 존재하는지 확인
            if (isTableExists(tableName)) {
                System.out.println(tableName + " 테이블이 이미 존재합니다.");
                return;
            }
            // 테이블이 없으면 생성
            createTable(tableName);

            // CSV 파일의 데이터를 테이블에 삽입
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                insertData(tableName, data);
            }

            System.out.println("Links 데이터 삽입 완료");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isTableExists(String tableName) {
        // 테이블이 존재하는지 확인하는 쿼리
        String query = "SELECT 1 FROM " + tableName + " LIMIT 1";
        try {
            jdbcTemplate.queryForObject(query, Integer.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void createTable(String tableName) {
        // 테이블 생성 쿼리 작성
        String createTableQuery = "CREATE TABLE " + tableName + " (" +
                "movieId BIGINT, " +
                "imdbId BIGINT, " +
                "tmdbId BIGINT " +
                ")";
        jdbcTemplate.execute(createTableQuery);
        System.out.println("Links 테이블 생성 완료");
    }

    private void insertData(String tableName, String[] data) {
        if (data.length < 2 || data.length > 3) {
            System.err.println("잘못된 데이터 형식입니다: " + String.join(",", data));
            return;
        }
        // 데이터 삽입 쿼리 작성
        String insertQuery;
        if (data.length == 2) {//links.csv에 tmdbId가 없는 데이터들이 있어 그것들도 삽입될수있게함.
            insertQuery = "INSERT INTO " + tableName + " (movieId,imdbId) VALUES (?, ?)";
            jdbcTemplate.update(insertQuery, data[1],Long.parseLong(data[0]));
        } else {
            insertQuery = "INSERT INTO " + tableName + " (movieId, imdbId, tmdbId) VALUES (?, ?, ?)";
            jdbcTemplate.update(insertQuery, Long.parseLong(data[0]), data[1], Long.parseLong(data[2]));
        }
    }

}