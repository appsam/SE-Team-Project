package DB;
import java.sql.*;
import java.util.Scanner;

public class db_search {

    public static void main(String[] args) {
        String JDBCURL = "jdbc:mysql://localhost:3306/moviedb";
        String username = "root";
        String password = "TCB3542187*";
        while (true) {
            try (Connection conn = DriverManager.getConnection(JDBCURL, username, password)) {
                // 사용자 입력을 받아 영화 제목 검색
                Scanner s = new Scanner(System.in);
                System.out.print("영화 제목 입력 :");
                String searchTitle = s.nextLine();
                if (searchTitle.equals("종료")) break;
                // SQL 쿼리 작성
                String query = "SELECT m.title, m.genres, AVG(r.rating) AS averageRating, t.tag " +
                        "FROM movies m " +
                        "JOIN ratings r ON m.movieId = r.movieId " +
                        "JOIN tags t ON m.movieId = t.movieId " +
                        "WHERE m.title LIKE ? " +
                        "GROUP BY m.title, m.genres, t.tag";

                // PreparedStatement를 사용하여 SQL 쿼리 준비
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, "%" + searchTitle + "%");

                // 쿼리 실행 및 결과 검색
                ResultSet resultSet = preparedStatement.executeQuery();

                // 결과 출력
                boolean found = false;
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String genres = resultSet.getString("genres");
                    double averageRating = resultSet.getDouble("averageRating");
                    String tag = resultSet.getString("tag");
                    System.out.println("제목: " + title);
                    System.out.println("장르: " + genres);
                    System.out.println("평점: " + averageRating);
                    System.out.println("태그: " + tag+"\n");
                    found = true;
                }
                if (!found) {
                    System.out.println(searchTitle + "에 해당하는 영화가 없습니다.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
