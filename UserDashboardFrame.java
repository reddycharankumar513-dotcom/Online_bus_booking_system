import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UserDashboardFrame extends JFrame {

    User       user;
    BusDAO     busDAO     = new BusDAO();
    BookingDAO bookingDAO = new BookingDAO();

    DefaultTableModel allBusModel, bookingModel;
    JTextField        fromField, toField, dateField;

    public UserDashboardFrame(User user) {
        this.user = user;

        setTitle("goBus - Welcome, " + user.getName());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 100, 200));
        header.setPreferredSize(new Dimension(960, 50));

        JLabel titleLbl = new JLabel("  goBus");
        titleLbl.setFont(new Font("Arial", Font.BOLD, 20));
        titleLbl.setForeground(Color.WHITE);
        header.add(titleLbl, BorderLayout.WEST);

        JLabel welcomeLbl = new JLabel("Welcome, " + user.getName() + "  ");
        welcomeLbl.setFont(new Font("Arial", Font.PLAIN, 13));
        welcomeLbl.setForeground(Color.WHITE);
        header.add(welcomeLbl, BorderLayout.EAST);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Search Bus",  buildSearchPanel());
        tabs.addTab("All Buses",   buildAllBusesPanel());
        tabs.addTab("My Bookings", buildMyBookingsPanel());

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
    // Tab 1: Search Bus
    // -------------------------------------------------------
    JPanel buildSearchPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 250, 255));

        JLabel fromLbl = new JLabel("From:");
        fromLbl.setBounds(30, 30, 50, 25);
        panel.add(fromLbl);
        fromField = new JTextField();
        fromField.setBounds(80, 30, 130, 28);
        panel.add(fromField);

        JLabel toLbl = new JLabel("To:");
        toLbl.setBounds(230, 30, 30, 25);
        panel.add(toLbl);
        toField = new JTextField();
        toField.setBounds(260, 30, 130, 28);
        panel.add(toField);

        JLabel dateLbl = new JLabel("Date:");
        dateLbl.setBounds(410, 30, 45, 25);
        panel.add(dateLbl);
        dateField = new JTextField("DD-MM-YYYY");
        dateField.setBounds(455, 30, 130, 28);
        panel.add(dateField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(605, 28, 100, 30);
        searchBtn.setBackground(new Color(30, 100, 200));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        panel.add(searchBtn);

        String[] cols = {"ID", "Bus Name", "Number", "From", "Via", "To", "Date", "Time", "Available Seats", "Price"};
        DefaultTableModel searchModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(searchModel);
        table.setRowHeight(24);
        table.getTableHeader().setBackground(new Color(30, 100, 200));
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 75, 900, 340);
        panel.add(scroll);

        JButton bookBtn = new JButton("Book Selected Bus");
        bookBtn.setBounds(360, 430, 200, 34);
        bookBtn.setBackground(new Color(50, 180, 50));
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setFocusPainted(false);
        panel.add(bookBtn);

        searchBtn.addActionListener(e -> {
            String from = fromField.getText().trim();
            String to   = toField.getText().trim();
            String date = dateField.getText().trim();
            if (from.isEmpty() || to.isEmpty() || date.equals("DD-MM-YYYY")) {
                JOptionPane.showMessageDialog(this, "Please enter From, To and Date.");
                return;
            }
            // Validate date format DD-MM-YYYY
            try {
                java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
                java.time.LocalDate.parse(date, fmt);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format! Please enter DD-MM-YYYY.\nExample: 10-05-2026");
                return;
            }
            searchModel.setRowCount(0);
            List<Bus> buses = busDAO.searchBuses(from, to, date);
            if (buses.isEmpty()) { JOptionPane.showMessageDialog(this, "No buses found."); return; }
            for (Bus b : buses) addBusRow(searchModel, b);
        });

        bookBtn.addActionListener(e -> bookBus(table, searchModel));
        return panel;
    }

    // -------------------------------------------------------
    // Tab 2: All Buses
    // -------------------------------------------------------
    JPanel buildAllBusesPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] cols = {"ID", "Bus Name", "Number", "From", "Via", "To", "Date", "Time", "Available Seats", "Price"};
        allBusModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(allBusModel);
        table.setRowHeight(24);
        table.getTableHeader().setBackground(new Color(30, 100, 200));
        table.getTableHeader().setForeground(Color.WHITE);
        loadAllBuses();

        JPanel bottom = new JPanel();
        JButton bookBtn = new JButton("Book Selected Bus");
        bookBtn.setBackground(new Color(50, 180, 50));
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setFocusPainted(false);
        bottom.add(bookBtn);
        bookBtn.addActionListener(e -> bookBus(table, allBusModel));

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    void loadAllBuses() {
        allBusModel.setRowCount(0);
        for (Bus b : busDAO.getAllBuses()) addBusRow(allBusModel, b);
    }

    // -------------------------------------------------------
    // Tab 3: My Bookings
    // -------------------------------------------------------
    JPanel buildMyBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] cols = {"Booking ID", "Bus Name", "From", "To", "Seats", "Total Price", "Date"};
        bookingModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(bookingModel);
        table.setRowHeight(24);
        table.getTableHeader().setBackground(new Color(30, 100, 200));
        table.getTableHeader().setForeground(Color.WHITE);
        loadMyBookings();

        JPanel bottom = new JPanel();
        JButton cancelBtn = new JButton("Cancel Booking");
        cancelBtn.setBackground(new Color(200, 50, 50));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        bottom.add(cancelBtn);

        cancelBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a booking."); return; }

            int bookingId = (int) bookingModel.getValueAt(row, 0);

            // Get bus ID from database using booking ID
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking == null) { JOptionPane.showMessageDialog(this, "Booking not found."); return; }

            int busId    = booking.getBusId();
            int numSeats = booking.getNumSeats();
            int confirm = JOptionPane.showConfirmDialog(this,
                "Cancel this booking?", "Confirm", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean deleted = bookingDAO.deleteBooking(bookingId);
                if (deleted) {
                    busDAO.restoreSeats(busId, numSeats);
                    JOptionPane.showMessageDialog(this, "Booking cancelled successfully.");
                    loadMyBookings();
                    loadAllBuses();
                } else {
                    JOptionPane.showMessageDialog(this, "Could not cancel booking.");
                }
            }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    void loadMyBookings() {
        bookingModel.setRowCount(0);
        for (Booking b : bookingDAO.getBookingsByUser(user.getUserId())) {
            Bus bus = busDAO.getBusById(b.getBusId());
            String busName = (bus != null) ? bus.getBusName()      : "Unknown";
            String from    = (bus != null) ? bus.getFromLocation() : "-";
            String to      = (bus != null) ? bus.getToLocation()   : "-";
            bookingModel.addRow(new Object[]{
                b.getBookingId(), busName, from, to,
                b.getNumSeats(), "Rs." + b.getTotalPrice(), b.getBookingDate()
            });
        }
    }

    // -------------------------------------------------------
    // Book a bus
    // -------------------------------------------------------
    void bookBus(JTable table, DefaultTableModel model) {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a bus first."); return; }

        int    busId     = (int)    model.getValueAt(row, 0);
        String busName   = (String) model.getValueAt(row, 1);
        String via       = (String) model.getValueAt(row, 4);
        int    available = (int)    model.getValueAt(row, 8);
        double price     = Double.parseDouble(model.getValueAt(row, 9).toString().replace("Rs.", ""));

        if (available == 0) { JOptionPane.showMessageDialog(this, "No seats available!"); return; }

        String input = JOptionPane.showInputDialog(this,
            "Bus             : " + busName +
            "\nVia             : " + via +
            "\nAvailable Seats : " + available +
            "\nPrice per seat  : Rs." + price +
            "\n\nEnter number of seats:");

        if (input == null || input.trim().isEmpty()) return;

        int numSeats;
        try {
            numSeats = Integer.parseInt(input.trim());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid number.");
            return;
        }

        if (numSeats <= 0 || numSeats > available) {
            JOptionPane.showMessageDialog(this, "Invalid! Only " + available + " seats available.");
            return;
        }

        double total = numSeats * price;
        int confirm = JOptionPane.showConfirmDialog(this,
            "Total Amount : Rs." + total + "\nConfirm booking?",
            "Confirm", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean booked = bookingDAO.addBooking(
                user.getUserId(), busId, numSeats, total,
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            if (booked) {
                busDAO.reduceSeats(busId, numSeats);
                JOptionPane.showMessageDialog(this, "Booking Confirmed!\nTotal Paid: Rs." + total);
                loadMyBookings();
                loadAllBuses();
            } else {
                JOptionPane.showMessageDialog(this, "Booking failed. Try again.");
            }
        }
    }

    void addBusRow(DefaultTableModel model, Bus b) {
        model.addRow(new Object[]{
            b.getBusId(),        b.getBusName(),      b.getBusNumber(),
            b.getFromLocation(), b.getVia(),          b.getToLocation(),
            b.getTravelDate(),   b.getDepartureTime(),
            b.getAvailableSeats(), "Rs." + b.getPrice()
        });
    }
}
