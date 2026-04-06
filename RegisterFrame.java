import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    JTextField     nameField, emailField, phoneField;
    JPasswordField passwordField;
    UserDAO        userDAO = new UserDAO();

    public RegisterFrame() {
        setTitle("goBus - Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));

        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(440, 510));
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 255), 2));

        // Header bar
        JPanel headerBar = new JPanel(null);
        headerBar.setBackground(new Color(30, 100, 200));
        headerBar.setBounds(0, 0, 440, 65);
        card.add(headerBar);

        JLabel title = new JLabel("User Registration", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 0, 440, 65);
        headerBar.add(title);

        JLabel nameLbl = new JLabel("Full Name:");
        nameLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLbl.setBounds(60, 90, 120, 25);
        card.add(nameLbl);
        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setBounds(60, 115, 320, 36);
        card.add(nameField);

        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        emailLbl.setBounds(60, 165, 120, 25);
        card.add(emailLbl);
        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBounds(60, 190, 320, 36);
        card.add(emailField);

        JLabel phoneLbl = new JLabel("Phone:");
        phoneLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        phoneLbl.setBounds(60, 240, 120, 25);
        card.add(phoneLbl);
        phoneField = new JTextField();
        phoneField.setFont(new Font("Arial", Font.PLAIN, 14));
        phoneField.setBounds(60, 265, 320, 36);
        card.add(phoneField);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        passLbl.setBounds(60, 315, 120, 25);
        card.add(passLbl);
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBounds(60, 340, 320, 36);
        card.add(passwordField);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(60, 400, 145, 42);
        registerBtn.setBackground(new Color(50, 180, 50));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        registerBtn.setFocusPainted(false);
        registerBtn.setBorderPainted(false);
        card.add(registerBtn);

        JButton backBtn = new JButton("<- Back");
        backBtn.setBounds(235, 400, 145, 42);
        backBtn.setBackground(Color.GRAY);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        card.add(backBtn);

        JLabel footer = new JLabel("Aditya University  |  2025-26", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.PLAIN, 12));
        footer.setForeground(Color.LIGHT_GRAY);
        footer.setBounds(0, 465, 440, 25);
        card.add(footer);

        panel.add(card);
        add(panel);

        registerBtn.addActionListener(e -> {
            String name     = nameField.getText().trim();
            String email    = emailField.getText().trim();
            String phone    = phoneField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }
            if (userDAO.emailExists(email)) {
                JOptionPane.showMessageDialog(this, "Email already registered. Please login.");
                return;
            }
            String error = userDAO.register(name, email, phone, password);
            if (error == null) {
                JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
                dispose();
                new LoginFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Registration failed.\n\nError: " + error,
                    "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }
}
