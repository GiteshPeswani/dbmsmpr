import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLogin {

    public static boolean loginUser(String username, String password) {
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        boolean isLoggedIn = false;

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);  // In production, hash the password

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isLoggedIn = true;  // Login successful
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid username or password.");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isLoggedIn;
    }

    public static void main(String[] args) {
        loginUser("john_doe", "password123");
    }
}
