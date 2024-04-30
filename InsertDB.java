package DB;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.File;
public class InsertDB {
		//CSV������ �о� MySQL�� �״�� ���̺� ����
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String csvFile = "C:\\Users\\MS\\Downloads\\ml-latest-small\\ratings.csv"; // CSV ���� ���
        String jdbcURL = "jdbc:mysql://localhost:3306/moviedb"; // MySQL �ּ�
        String username = "root";
        String password = "TCB3542187*"; // MySQL ��й�ȣ

        try (Connection conn = DriverManager.getConnection(jdbcURL, username, password);
             BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            // CSV ���� �̸����� Ȯ���� �����Ͽ� ���̺� �̸����� ���
            String tableName = new File(csvFile).getName().replaceFirst("[.][^.]+$", "");

            // ���̺� ���� ���� �ۼ�
            StringBuilder createTableQuery = new StringBuilder(" " + tableName + " (");
            String headerLine = br.readLine();
            String[] columns = headerLine.split(",");
            for (int i = 0; i < columns.length; i++) {
                createTableQuery.append(columns[i]).append(" VARCHAR(255)");	//������ �ϴ� ���� ���� 255�� ����
                if (i < columns.length - 1) {
                    createTableQuery.append(", ");
                }
            }
            createTableQuery.append(");");

            // ���̺� ����
            try (PreparedStatement createTableStatement = conn.prepareStatement(createTableQuery.toString())) {
                createTableStatement.executeUpdate();
                System.out.println("���̺� ���� �Ϸ�");
            }

            // ������ ���� ���� �ۼ�
            String insertQuery = "INSERT INTO " + tableName + " VALUES (";
            for (int i = 0; i < columns.length; i++) {
                insertQuery += "?,";
            }
            insertQuery = insertQuery.substring(0, insertQuery.length() - 1) + ")";

            // CSV ������ �����͸� ���̺� ����
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                try (PreparedStatement insertStatement = conn.prepareStatement(insertQuery)) {
                    for (int i = 0; i < data.length; i++) {
                        // PreparedStatement�� ���� �Ҵ��մϴ�. ���⼭�� 1���� �����մϴ�.
                        insertStatement.setString(i + 1, data[i]);
                    }
                    // ������ �����մϴ�.
                    insertStatement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("������ ���� �Ϸ�");

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
