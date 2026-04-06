import java.sql.*;

public class UserDAO {

    // Returns null on success, or the error message string on failure
    public String register(String name, String email, String phone, String password) {
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) return "Database connection failed. Check your MySQL password in DBConnection.java";
            String sql = "INSERT INTO users (name, email, phone, password) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, password);
            int rows = ps.executeUpdate();
            con.close();
            return rows > 0 ? null : "Insert returned 0 rows.";
        } catch (Exception e) {
            return e.getMessage(); // return real error
        }
    }

    public User login(String email, String password) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("password")
                );
                con.close();
                return u;
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }

    public boolean emailExists(String email) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT user_id FROM users WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            boolean found = rs.next();
            con.close();
            return found;
        } catch (Exception e) {
            System.out.println("Email check error: " + e.getMessage());
        }
        return false;
    }

    public int getTotalUsers() {
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM users");
            if (rs.next()) { int c = rs.getInt(1); con.close(); return c; }
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return 0;
    }
}
