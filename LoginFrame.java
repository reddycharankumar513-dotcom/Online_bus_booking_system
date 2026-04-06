import java.awt.*;
import javax.swing.*;

public class LoginFrame extends JFrame {

    JTextField     emailField;
    JPasswordField passwordField;
    UserDAO        userDAO = new UserDAO();

    public LoginFrame() {
        setTitle("goBus - User Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));

        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(440, 460));
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 255), 2));

        // Header bar
        JPanel headerBar = new JPanel(null);
        headerBar.setBackground(new Color(30, 100, 200));
        headerBar.setBounds(0, 0, 440, 65);
        card.add(headerBar);

        JLabel title = new JLabel("User Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 0, 440, 65);
        headerBar.add(title);

        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        emailLbl.setBounds(60, 100, 100, 25);
        card.add(emailLbl);
        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBounds(60, 128, 320, 36);
        card.add(emailField);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        passLbl.setBounds(60, 182, 100, 25);
        card.add(passLbl);
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBounds(60, 210, 320, 36);
        card.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(60, 278, 145, 42);
        loginBtn.setBackground(new Color(30, 100, 200));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        card.add(loginBtn);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(235, 278, 145, 42);
        registerBtn.setBackground(new Color(50, 180, 50));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        registerBtn.setFocusPainted(false);
        registerBtn.setBorderPainted(false);
        card.add(registerBtn);

        JButton backBtn = new JButton("<- Back to Home");
        backBtn.setBounds(130, 338, 180, 30);
        backBtn.setBackground(Color.WHITE);
        backBtn.setForeground(new Color(30, 100, 200));
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        card.add(backBtn);

        JLabel footer = new JLabel("Aditya University  |  2025-26", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.PLAIN, 12));
        footer.setForeground(Color.LIGHT_GRAY);
        footer.setBounds(0, 410, 440, 25);
        card.add(footer);

        panel.add(card);
        add(panel);

        loginBtn.addActionListener(e -> {
            String email    = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter email and password.");
                return;
            }
            User user = userDAO.login(email, password);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Invalid email or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dispose();
            new UserDashboardFrame(user).setVisible(true);
        });

        registerBtn.addActionListener(e -> { dispose(); new RegisterFrame().setVisible(true); });
        backBtn.addActionListener(e -> { dispose(); new WelcomeFrame().setVisible(true); });
    }
}
