sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class DBConnection {

    static String url      = "jdbc:mysql://localhost:3306/gobus1?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    static String user     = "root";
    static String password = "root"; // <-- change this to your MySQL password

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                "MySQL Driver not found!\n\nMake sure mysql-connector.jar is added to your classpath.\n\nError: " + e.getMessage(),
                "Driver Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Database Connection Failed!\n\nError: " + e.getMessage(),
                "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
        return con;
    }
}
