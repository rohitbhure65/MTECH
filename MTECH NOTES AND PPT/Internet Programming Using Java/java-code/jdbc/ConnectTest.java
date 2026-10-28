import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** The 7 JDBC steps in one small program. */
public class ConnectTest {
    public static void main(String[] args) {
        String url  = "jdbc:mysql://localhost:3306/college?serverTimezone=UTC";
        String user = "root";
        String pass = "root123";

        try {
            // Step 1: load the driver (optional with JDBC 4+, but good for exams)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: open a connection
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to " + con.getMetaData().getDatabaseProductName());

            // Step 3: create a statement
            Statement st = con.createStatement();

            // Step 4: execute the query
            ResultSet rs = st.executeQuery("SELECT id, name, course FROM students");

            // Step 5: process the ResultSet (cursor starts BEFORE the first row)
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "  " + rs.getString("name") + "  " + rs.getString(3));
            }

            // Step 6: close everything (reverse order)
            rs.close();
            st.close();
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver jar not on classpath: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage() + "  (code " + e.getErrorCode() + ")");
        }
    }
}
