import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

/** CallableStatement: call the stored procedure get_student(IN id, OUT name). */
public class ProcedureDemo {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/college?serverTimezone=UTC";
        try (Connection con = DriverManager.getConnection(url, "root", "root123");
             CallableStatement cs = con.prepareCall("{call get_student(?, ?)}")) {
            cs.setInt(1, 1);                          // IN parameter
            cs.registerOutParameter(2, Types.VARCHAR);   // OUT parameter
            cs.execute();
            System.out.println("Student 1 is " + cs.getString(2));
        }
    }
}
