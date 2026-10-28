import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/** Console menu: Create / Read / Update / Delete on the students table. */
public class StudentCrud {
    static final String URL  = "jdbc:mysql://localhost:3306/college?serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "root123";

    static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // C - INSERT
    static void create(String name, String email, String course) throws SQLException {
        String sql = "INSERT INTO students(name, email, course) VALUES (?, ?, ?)";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, course);
            int rows = ps.executeUpdate();                 // returns number of rows changed
            System.out.println(rows + " row inserted.");
        }
    }

    // R - SELECT
    static void readAll() throws SQLException {
        String sql = "SELECT id, name, email, course FROM students ORDER BY id";
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.printf("%-4s %-15s %-25s %-10s%n", "ID", "NAME", "EMAIL", "COURSE");
            while (rs.next()) {
                System.out.printf("%-4d %-15s %-25s %-10s%n",
                        rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("course"));
            }
        }
    }

    // U - UPDATE
    static void update(int id, String course) throws SQLException {
        String sql = "UPDATE students SET course = ? WHERE id = ?";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, course);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            System.out.println(rows == 0 ? "No student with id " + id : rows + " row updated.");
        }
    }

    // D - DELETE
    static void delete(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows == 0 ? "No student with id " + id : rows + " row deleted.");
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n1.Add  2.List  3.Update course  4.Delete  5.Exit");
                System.out.print("Choice: ");
                int choice = Integer.parseInt(sc.nextLine().trim());
                try {
                    switch (choice) {
                        case 1:
                            System.out.print("Name: ");   String n = sc.nextLine();
                            System.out.print("Email: ");  String e = sc.nextLine();
                            System.out.print("Course: "); String c = sc.nextLine();
                            create(n, e, c);
                            break;
                        case 2:
                            readAll();
                            break;
                        case 3:
                            System.out.print("Id: ");         int uid = Integer.parseInt(sc.nextLine().trim());
                            System.out.print("New course: "); update(uid, sc.nextLine());
                            break;
                        case 4:
                            System.out.print("Id: ");
                            delete(Integer.parseInt(sc.nextLine().trim()));
                            break;
                        case 5:
                            System.out.println("Bye!");
                            return;
                        default:
                            System.out.println("Choose 1-5.");
                    }
                } catch (SQLException ex) {
                    System.out.println("Database error: " + ex.getMessage());
                }
            }
        }
    }
}
