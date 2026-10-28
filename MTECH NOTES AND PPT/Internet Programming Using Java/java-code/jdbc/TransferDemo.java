import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** Transaction: move money from one account to another - BOTH updates succeed or NONE. */
public class TransferDemo {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/college?serverTimezone=UTC";
        try (Connection con = DriverManager.getConnection(url, "root", "root123")) {
            con.setAutoCommit(false);                    // start a transaction
            try (PreparedStatement debit  = con.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE acc_no = ?");
                 PreparedStatement credit = con.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE acc_no = ?")) {

                BigDecimal amount = new BigDecimal("500.00");
                debit.setBigDecimal(1, amount);
                debit.setInt(2, 101);
                debit.executeUpdate();

                credit.setBigDecimal(1, amount);
                credit.setInt(2, 102);
                credit.executeUpdate();

                con.commit();                            // make both changes permanent
                System.out.println("Transfer done.");
            } catch (SQLException e) {
                con.rollback();                          // undo everything since setAutoCommit(false)
                System.out.println("Transfer failed, rolled back: " + e.getMessage());
            }
        }
    }
}
