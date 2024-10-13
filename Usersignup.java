//public class Usersignup {
    import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

    public class Usersignup {

        public static boolean checkUsernameExists(String username) {
            boolean exists = false;
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM Users WHERE username = ?";

            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    exists = true;  // Username already exists
                }
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return exists;
        }

        public static boolean signupUser(String username, String password) {
            Connection conn = DatabaseConnection.getConnection();
            String query = "INSERT INTO Users (username, password) VALUES (?, ?)";
            boolean isRegistered = false;

            try {
                // Check if username already exists
                if (checkUsernameExists(username)) {
                    System.out.println("Username already taken!");
                    return false;
                }

                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);  // In production, hash the password

                int result = ps.executeUpdate();
                if (result > 0) {
                    isRegistered = true;  // Signup successful
                    System.out.println("User registered successfully!");
                }
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return isRegistered;
        }

        public static void main(String[] args) {
            signupUser("john_doe", "password123");
        }
    }

