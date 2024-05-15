package InsertDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class InsertRatings implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    

    @Override
    public void run(String... args) throws Exception {
        String csvFile = "C:\\Users\\MS\\Desktop\\ml-latest-small\\ratings.csv"; // CSV 파일 경로
        String tableName = "ratings"; // 테이블 이름

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String headerLine = br.readLine();
            String[] columns = headerLine.split(",");
            if (isTableExists(tableName)) {
                System.out.println("Ratings 테이블이 이미 존재합니다.");
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

            System.out.println("Ratings 데이터 삽입 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean isTableExists(String tableName) {
        try {
            ResultSet resultSet = jdbcTemplate.getDataSource().getConnection().getMetaData().getTables(null, null, tableName, null);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
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
        String insertQuery = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(insertQuery, Long.parseLong(data[0]), Long.parseLong(data[1]),
                Double.parseDouble(data[2]), Long.parseLong(data[3]));
    }
}