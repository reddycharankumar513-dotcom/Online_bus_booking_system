import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class AdminLoginFrame extends JFrame {

    JTextField     usernameField;
    JPasswordField passwordField;

    public AdminLoginFrame() {
        setTitle("goBus - Admin Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 245, 245));

        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(440, 430));
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 180, 180), 2));

        // Header bar
        JPanel headerBar = new JPanel(null);
        headerBar.setBackground(new Color(180, 50, 50));
        headerBar.setBounds(0, 0, 440, 65);
        card.add(headerBar);

        JLabel title = new JLabel("Admin Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 0, 440, 65);
        headerBar.add(title);

        JLabel usernameLbl = new JLabel("Username:");
        usernameLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLbl.setBounds(60, 100, 120, 25);
        card.add(usernameLbl);
        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBounds(60, 128, 320, 36);
        card.add(usernameField);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        passLbl.setBounds(60, 182, 120, 25);
        card.add(passLbl);
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBounds(60, 210, 320, 36);
        card.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(60, 278, 145, 42);
        loginBtn.setBackground(new Color(180, 50, 50));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        card.add(loginBtn);

        JButton backBtn = new JButton("<- Back");
        backBtn.setBounds(235, 278, 145, 42);
        backBtn.setBackground(Color.GRAY);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        card.add(backBtn);

        JLabel footer = new JLabel("Aditya University  |  2025-26", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.PLAIN, 12));
        footer.setForeground(Color.LIGHT_GRAY);
        footer.setBounds(0, 380, 440, 25);
        card.add(footer);

        panel.add(card);
        add(panel);

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password.");
                return;
            }
            if (checkAdminLogin(username, password)) {
                dispose();
                new AdminFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        });

        backBtn.addActionListener(e -> { dispose(); new WelcomeFrame().setVisible(true); });
    }

    boolean checkAdminLogin(String username, String password) {
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) return false;
            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            boolean found = rs.next();
            con.close();
            return found;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Login error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
