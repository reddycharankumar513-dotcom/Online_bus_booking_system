import java.sql.*;
import java.util.*;

public class BusDAO {

    public List<Bus> getAllBuses() {
        List<Bus> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM buses");
            while (rs.next()) list.add(mapBus(rs));
            con.close();
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return list;
    }

    public List<Bus> searchBuses(String from, String to, String date) {
        List<Bus> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM buses WHERE from_location = ? AND to_location = ? AND travel_date = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, from);
            ps.setString(2, to);
            ps.setString(3, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapBus(rs));
            con.close();
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return list;
    }

    public Bus getBusById(int busId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM buses WHERE bus_id = ?");
            ps.setInt(1, busId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) { Bus b = mapBus(rs); con.close(); return b; }
            con.close();
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return null;
    }

    public boolean addBus(String name, String number, String from, String to, String via,
                          String date, String depTime, int seats, double price) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO buses (bus_name, bus_number, from_location, to_location, via, travel_date, departure_time, total_seats, available_seats, price) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);   ps.setString(2, number);
            ps.setString(3, from);   ps.setString(4, to);
            ps.setString(5, via);    ps.setString(6, date);
            ps.setString(7, depTime);
            ps.setInt(8, seats);     ps.setInt(9, seats);
            ps.setDouble(10, price);
            int rows = ps.executeUpdate();
            con.close();
            return rows > 0;
        } catch (Exception e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean deleteBus(int busId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM buses WHERE bus_id = ?");
            ps.setInt(1, busId);
            int rows = ps.executeUpdate();
            con.close();
            return rows > 0;
        } catch (Exception e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean reduceSeats(int busId, int seats) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "UPDATE buses SET available_seats = available_seats - ? WHERE bus_id = ?");
            ps.setInt(1, seats); ps.setInt(2, busId);
            int rows = ps.executeUpdate();
            con.close();
            return rows > 0;
        } catch (Exception e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean restoreSeats(int busId, int seats) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "UPDATE buses SET available_seats = available_seats + ? WHERE bus_id = ?");
            ps.setInt(1, seats); ps.setInt(2, busId);
            int rows = ps.executeUpdate();
            con.close();
            return rows > 0;
        } catch (Exception e) { System.out.println(e.getMessage()); return false; }
    }

    public int getTotalBuses() {
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM buses");
            if (rs.next()) { int c = rs.getInt(1); con.close(); return c; }
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return 0;
    }

    private Bus mapBus(ResultSet rs) throws Exception {
        return new Bus(
            rs.getInt("bus_id"),         rs.getString("bus_name"),
            rs.getString("bus_number"),  rs.getString("from_location"),
            rs.getString("to_location"), rs.getString("via"),
            rs.getString("travel_date"), rs.getString("departure_time"),
            rs.getInt("total_seats"),    rs.getInt("available_seats"),
            rs.getDouble("price")
        );
    }
}
