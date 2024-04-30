package DB;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.File;
public class InsertDB {
		//CSV파일을 읽어 MySQL에 그대로 테이블 생성
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String csvFile = "C:\\Users\\MS\\Downloads\\ml-latest-small\\ratings.csv"; // CSV 파일 경로
        String jdbcURL = "jdbc:mysql://localhost:3306/moviedb"; // MySQL 주소
        String username = "root";
        String password = "TCB3542187*"; // MySQL 비밀번호

        try (Connection conn = DriverManager.getConnection(jdbcURL, username, password);
             BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            // CSV 파일 이름에서 확장자 제거하여 테이블 이름으로 사용
            String tableName = new File(csvFile).getName().replaceFirst("[.][^.]+$", "");

            // 테이블 생성 쿼리 작성
            StringBuilder createTableQuery = new StringBuilder(" " + tableName + " (");
            String headerLine = br.readLine();
            String[] columns = headerLine.split(",");
            for (int i = 0; i < columns.length; i++) {
                createTableQuery.append(columns[i]).append(" VARCHAR(255)");	//형식은 일단 전부 문자 255개 제한
                if (i < columns.length - 1) {
                    createTableQuery.append(", ");
                }
            }
            createTableQuery.append(");");

            // 테이블 생성
            try (PreparedStatement createTableStatement = conn.prepareStatement(createTableQuery.toString())) {
                createTableStatement.executeUpdate();
                System.out.println("테이블 생성 완료");
            }

            // 데이터 삽입 쿼리 작성
            String insertQuery = "INSERT INTO " + tableName + " VALUES (";
            for (int i = 0; i < columns.length; i++) {
                insertQuery += "?,";
            }
            insertQuery = insertQuery.substring(0, insertQuery.length() - 1) + ")";

            // CSV 파일의 데이터를 테이블에 삽입
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                try (PreparedStatement insertStatement = conn.prepareStatement(insertQuery)) {
                    for (int i = 0; i < data.length; i++) {
                        // PreparedStatement에 값을 할당합니다. 여기서는 1부터 시작합니다.
                        insertStatement.setString(i + 1, data[i]);
                    }
                    // 쿼리를 실행합니다.
                    insertStatement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("데이터 삽입 완료");

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
