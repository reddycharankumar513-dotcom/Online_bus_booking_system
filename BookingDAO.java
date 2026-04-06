import java.sql.*;
import java.util.*;

public class BookingDAO {

    public boolean addBooking(int userId, int busId, int numSeats, double totalPrice, String date) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO bookings (user_id, bus_id, num_seats, total_price, booking_date) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);    ps.setInt(2, busId);
            ps.setInt(3, numSeats);  ps.setDouble(4, totalPrice);
            ps.setString(5, date);
            int rows = ps.executeUpdate();
            con.close();
            return rows > 0;
        } catch (Exception e) { System.out.println(e.getMessage()); return false; }
    }

    public List<Booking> getBookingsByUser(int userId) {
        List<Booking> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM bookings WHERE user_id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapBooking(rs));
            con.close();
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return list;
    }

    public Booking getBookingById(int bookingId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM bookings WHERE booking_id = ?");
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) { Booking b = mapBooking(rs); con.close(); return b; }
            con.close();
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return null;
    }

    public boolean deleteBooking(int bookingId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM bookings WHERE booking_id = ?");
            ps.setInt(1, bookingId);
            int rows = ps.executeUpdate();
            con.close();
            return rows > 0;
        } catch (Exception e) { System.out.println(e.getMessage()); return false; }
    }

    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM bookings");
            while (rs.next()) list.add(mapBooking(rs));
            con.close();
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return list;
    }

    // Check if a bus has any existing bookings
    public boolean busHasBookings(int busId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT COUNT(*) FROM bookings WHERE bus_id = ?");
            ps.setInt(1, busId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                boolean has = rs.getInt(1) > 0;
                con.close();
                return has;
            }
            con.close();
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return false;
    }

    public int getTotalBookings() {
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM bookings");
            if (rs.next()) { int c = rs.getInt(1); con.close(); return c; }
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return 0;
    }

    public double getTotalRevenue() {
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT SUM(total_price) FROM bookings");
            if (rs.next()) { double t = rs.getDouble(1); con.close(); return t; }
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return 0.0;
    }

    private Booking mapBooking(ResultSet rs) throws Exception {
        return new Booking(
            rs.getInt("booking_id"), rs.getInt("user_id"),
            rs.getInt("bus_id"),     rs.getInt("num_seats"),
            rs.getDouble("total_price"), rs.getString("booking_date")
        );
    }
}
