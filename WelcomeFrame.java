import java.awt.*;
import javax.swing.*;

public class WelcomeFrame extends JFrame {

    public WelcomeFrame() {
        setTitle("goBus - Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Main panel with background
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));

        // Center card panel
        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(440, 420));
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 255), 2));

        JLabel logo = new JLabel("goBus", SwingConstants.CENTER);
        logo.setFont(new Font("Arial", Font.BOLD, 42));
        logo.setForeground(new Color(30, 100, 200));
        logo.setBounds(0, 40, 440, 55);
        card.add(logo);

        JLabel tagline = new JLabel("Bus Ticket Booking System", SwingConstants.CENTER);
        tagline.setFont(new Font("Arial", Font.PLAIN, 15));
        tagline.setForeground(Color.GRAY);
        tagline.setBounds(0, 100, 440, 25);
        card.add(tagline);

        JSeparator sep = new JSeparator();
        sep.setBounds(40, 140, 360, 2);
        card.add(sep);

        JLabel question = new JLabel("Who are you?", SwingConstants.CENTER);
        question.setFont(new Font("Arial", Font.BOLD, 17));
        question.setForeground(new Color(50, 50, 50));
        question.setBounds(0, 165, 440, 30);
        card.add(question);

        JButton userBtn = new JButton("Login as User");
        userBtn.setBounds(70, 215, 300, 55);
        userBtn.setBackground(new Color(30, 100, 200));
        userBtn.setForeground(Color.WHITE);
        userBtn.setFont(new Font("Arial", Font.BOLD, 16));
        userBtn.setFocusPainted(false);
        userBtn.setBorderPainted(false);
        card.add(userBtn);

        JButton adminBtn = new JButton("Login as Admin");
        adminBtn.setBounds(70, 290, 300, 55);
        adminBtn.setBackground(new Color(180, 50, 50));
        adminBtn.setForeground(Color.WHITE);
        adminBtn.setFont(new Font("Arial", Font.BOLD, 16));
        adminBtn.setFocusPainted(false);
        adminBtn.setBorderPainted(false);
        card.add(adminBtn);

        JLabel footer = new JLabel("Aditya University  |  2025-26", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.PLAIN, 12));
        footer.setForeground(Color.LIGHT_GRAY);
        footer.setBounds(0, 370, 440, 25);
        card.add(footer);

        panel.add(card);
        add(panel);

        userBtn.addActionListener(e -> { dispose(); new LoginFrame().setVisible(true); });
        adminBtn.addActionListener(e -> { dispose(); new AdminLoginFrame().setVisible(true); });
    }
}
