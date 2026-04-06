import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminFrame extends JFrame {

    BusDAO     busDAO     = new BusDAO();
    BookingDAO bookingDAO = new BookingDAO();
    UserDAO    userDAO    = new UserDAO();

    DefaultTableModel bookingModel, busModel;

    public AdminFrame() {
        setTitle("goBus - Admin Panel");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(180, 50, 50));
        header.setPreferredSize(new Dimension(980, 50));

        JLabel titleLbl = new JLabel("  goBus - Admin Panel");
        titleLbl.setFont(new Font("Arial", Font.BOLD, 20));
        titleLbl.setForeground(Color.WHITE);
        header.add(titleLbl, BorderLayout.WEST);

        JLabel adminLbl = new JLabel("Admin  ");
        adminLbl.setFont(new Font("Arial", Font.PLAIN, 13));
        adminLbl.setForeground(Color.WHITE);
        header.add(adminLbl, BorderLayout.EAST);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Dashboard",    buildDashboardPanel());
        tabs.addTab("Add Bus",      buildAddBusPanel());
        tabs.addTab("All Buses",    buildAllBusesPanel());
        tabs.addTab("All Bookings", buildAllBookingsPanel());

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(200, 50, 50));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        footer.add(logoutBtn);
        logoutBtn.addActionListener(e -> { dispose(); new WelcomeFrame().setVisible(true); });

        add(header, BorderLayout.NORTH);
        add(tabs,   BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }

    // -------------------------------------------------------
    // Dashboard
    // -------------------------------------------------------
    JPanel buildDashboardPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 250, 255));

        JLabel heading = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setBounds(0, 30, 980, 35);
        panel.add(heading);

        addCard(panel, "Total Users",    String.valueOf(userDAO.getTotalUsers()),      60,  120, new Color(70,  130, 220));
        addCard(panel, "Total Buses",    String.valueOf(busDAO.getTotalBuses()),        270, 120, new Color(50,  180, 80));
        addCard(panel, "Total Bookings", String.valueOf(bookingDAO.getTotalBookings()), 480, 120, new Color(230, 150, 50));
        addCard(panel, "Total Revenue",  "Rs." + bookingDAO.getTotalRevenue(),         690, 120, new Color(100, 60,  180));

        return panel;
    }

    void addCard(JPanel panel, String label, String value, int x, int y, Color color) {
        JPanel card = new JPanel(null);
        card.setBounds(x, y, 170, 110);
        card.setBackground(color);

        JLabel lbl = new JLabel(label, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(0, 18, 170, 25);
        card.add(lbl);

        JLabel val = new JLabel(value, SwingConstants.CENTER);
        val.setFont(new Font("Arial", Font.BOLD, 26));
        val.setForeground(Color.WHITE);
        val.setBounds(0, 50, 170, 38);
        card.add(val);

        panel.add(card);
    }

    // -------------------------------------------------------
    // Add Bus (with Via field)
    // -------------------------------------------------------
    JPanel buildAddBusPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 250, 255));

        JLabel title = new JLabel("Add New Bus");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(30, 100, 200));
        title.setBounds(60, 25, 200, 30);
        panel.add(title);

        String[]     labels = {"Bus Name:", "Bus Number:", "From:", "Via:", "To:", "Travel Date (DD-MM-YYYY):", "Departure Time:", "Total Seats:", "Price (Rs.):"};
        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setBounds(60, 75 + i * 46, 200, 25);
            panel.add(lbl);
            fields[i] = new JTextField();
            fields[i].setBounds(265, 75 + i * 46, 240, 28);
            panel.add(fields[i]);
        }

        JButton addBtn = new JButton("Add Bus");
        addBtn.setBounds(265, 75 + labels.length * 46, 120, 35);
        addBtn.setBackground(new Color(50, 180, 50));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        panel.add(addBtn);

        JLabel statusLbl = new JLabel("");
        statusLbl.setBounds(60, 75 + labels.length * 46 + 45, 400, 25);
        panel.add(statusLbl);

        addBtn.addActionListener(e -> {
            try {
                String name    = fields[0].getText().trim();
                String number  = fields[1].getText().trim();
                String from    = fields[2].getText().trim();
                String via     = fields[3].getText().trim();
                String to      = fields[4].getText().trim();
                String date    = fields[5].getText().trim();
                String depTime = fields[6].getText().trim();
                int    seats   = Integer.parseInt(fields[7].getText().trim());
                double price   = Double.parseDouble(fields[8].getText().trim());

                if (name.isEmpty() || from.isEmpty() || to.isEmpty() || date.isEmpty()) {
                    statusLbl.setForeground(Color.RED);
                    statusLbl.setText("Please fill all fields.");
                    return;
                }

                // Validate date format DD-MM-YYYY
                try {
                    java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    java.time.LocalDate.parse(date, fmt);
                } catch (Exception ex) {
                    statusLbl.setForeground(Color.RED);
                    statusLbl.setText("Invalid date! Use DD-MM-YYYY format.");
                    return;
                }

                boolean ok = busDAO.addBus(name, number, from, to, via, date, depTime, seats, price);
                if (ok) {
                    statusLbl.setForeground(new Color(50, 150, 50));
                    statusLbl.setText("Bus added successfully!");
                    for (JTextField f : fields) f.setText("");
                    loadAllBuses();
                } else {
                    statusLbl.setForeground(Color.RED);
                    statusLbl.setText("Failed to add bus.");
                }
            } catch (Exception ex) {
                statusLbl.setForeground(Color.RED);
                statusLbl.setText("Enter valid seats and price.");
            }
        });

        return panel;
    }

    // -------------------------------------------------------
    // All Buses (with Via column)
    // -------------------------------------------------------
    JPanel buildAllBusesPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] cols = {"ID", "Bus Name", "Number", "From", "Via", "To", "Date", "Time", "Total Seats", "Available", "Price"};
        busModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(busModel);
        table.setRowHeight(24);
        table.getTableHeader().setBackground(new Color(180, 50, 50));
        table.getTableHeader().setForeground(Color.WHITE);
        loadAllBuses();

        JPanel bottom = new JPanel();
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFocusPainted(false);
        bottom.add(refreshBtn);

        JButton deleteBtn = new JButton("Delete Selected Bus");
        deleteBtn.setBackground(new Color(200, 50, 50));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        bottom.add(deleteBtn);

        refreshBtn.addActionListener(e -> loadAllBuses());

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a bus to delete.");
                return;
            }
            int busId   = (int)    busModel.getValueAt(row, 0);
            String name = (String) busModel.getValueAt(row, 1);

            // Check if this bus has any bookings
            if (bookingDAO.busHasBookings(busId)) {
                JOptionPane.showMessageDialog(null,
                    "Cannot delete \"" + name + "\".\n\n" +
                    "This bus has existing bookings.\n" +
                    "Please cancel all its bookings first before deleting.",
                    "Delete Not Allowed", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete bus:\n" + name + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = busDAO.deleteBus(busId);
                if (ok) {
                    JOptionPane.showMessageDialog(null, "Bus deleted successfully!");
                    loadAllBuses();
                } else {
                    JOptionPane.showMessageDialog(null, "Could not delete bus. Try again.");
                }
            }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    void loadAllBuses() {
        if (busModel == null) return;
        busModel.setRowCount(0);
        for (Bus b : busDAO.getAllBuses()) {
            busModel.addRow(new Object[]{
                b.getBusId(),        b.getBusName(),      b.getBusNumber(),
                b.getFromLocation(), b.getVia(),          b.getToLocation(),
                b.getTravelDate(),   b.getDepartureTime(),
                b.getTotalSeats(),   b.getAvailableSeats(), "Rs." + b.getPrice()
            });
        }
    }

    // -------------------------------------------------------
    // All Bookings
    // -------------------------------------------------------
    JPanel buildAllBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] cols = {"Booking ID", "User ID", "Bus ID", "Seats", "Total Price", "Booking Date"};
        bookingModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(bookingModel);
        table.setRowHeight(24);
        table.getTableHeader().setBackground(new Color(180, 50, 50));
        table.getTableHeader().setForeground(Color.WHITE);
        loadAllBookings();

        JPanel bottom = new JPanel();
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFocusPainted(false);
        bottom.add(refreshBtn);
        refreshBtn.addActionListener(e -> loadAllBookings());

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    void loadAllBookings() {
        bookingModel.setRowCount(0);
        for (Booking b : bookingDAO.getAllBookings()) {
            bookingModel.addRow(new Object[]{
                b.getBookingId(), b.getUserId(), b.getBusId(),
                b.getNumSeats(), "Rs." + b.getTotalPrice(), b.getBookingDate()
            });
        }
    }
}
