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
                // ����� �Է��� �޾� ��ȭ ���� �˻�
                Scanner s = new Scanner(System.in);
                System.out.print("��ȭ ���� �Է� :");
                String searchTitle = s.nextLine();
                if (searchTitle.equals("����")) break;
                // SQL ���� �ۼ�
                String query = "SELECT m.title, m.genres, AVG(r.rating) AS averageRating, t.tag " +
                        "FROM movies m " +
                        "JOIN ratings r ON m.movieId = r.movieId " +
                        "JOIN tags t ON m.movieId = t.movieId " +
                        "WHERE m.title LIKE ? " +
                        "GROUP BY m.title, m.genres, t.tag";

                // PreparedStatement�� ����Ͽ� SQL ���� �غ�
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, "%" + searchTitle + "%");

                // ���� ���� �� ��� �˻�
                ResultSet resultSet = preparedStatement.executeQuery();

                // ��� ���
                boolean found = false;
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String genres = resultSet.getString("genres");
                    double averageRating = resultSet.getDouble("averageRating");
                    String tag = resultSet.getString("tag");
                    System.out.println("����: " + title);
                    System.out.println("�帣: " + genres);
                    System.out.println("����: " + averageRating);
                    System.out.println("�±�: " + tag+"\n");
                    found = true;
                }
                if (!found) {
                    System.out.println(searchTitle + "�� �ش��ϴ� ��ȭ�� �����ϴ�.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
