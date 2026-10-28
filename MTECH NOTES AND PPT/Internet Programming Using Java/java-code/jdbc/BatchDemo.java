import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** Batch: send many INSERTs together (much faster than one by one). */
public class BatchDemo {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/college?serverTimezone=UTC";
        String sql = "INSERT INTO students(name, email, course) VALUES (?, ?, ?)";
        String[][] data = { {"Kiran", "kiran@example.com", "MCA"}, {"Neha", "neha@example.com", "BCA"}, {"Omkar", "omkar@example.com", "M.Tech"} };

        try (Connection con = DriverManager.getConnection(url, "root", "root123");
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (String[] row : data) {
                ps.setString(1, row[0]);
                ps.setString(2, row[1]);
                ps.setString(3, row[2]);
                ps.addBatch();                       // queue it
            }
            int[] counts = ps.executeBatch();        // send all at once
            System.out.println(counts.length + " statements executed.");
        }
    }
}
