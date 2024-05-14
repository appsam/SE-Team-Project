package InsertDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class InsertMember implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

 

    @Override
    public void run(String... args) throws Exception {
        String tableName = "Member"; // 테이블 이름

        if (!isTableExists(tableName)) {
            createTable(tableName);
            System.out.println("Member 테이블 생성 완료");
        } else {
            System.out.println("Member 테이블이 이미 존재합니다.");
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
        String createTableQuery = "CREATE TABLE " + tableName + " (" +
                "member_id BIGINT PRIMARY KEY," +
                "login_id VARCHAR(100)," +
                "password VARCHAR(100)," +
                "name VARCHAR(100)," +
                "role VARCHAR(100)" +
                ")";

        jdbcTemplate.execute(createTableQuery);
    }
}

