public class Booking {
    int    bookingId;
    int    userId;
    int    busId;
    int    numSeats;
    double totalPrice;
    String bookingDate;

    public Booking(int bookingId, int userId, int busId,
                   int numSeats, double totalPrice, String bookingDate) {
        this.bookingId   = bookingId;
        this.userId      = userId;
        this.busId       = busId;
        this.numSeats    = numSeats;
        this.totalPrice  = totalPrice;
        this.bookingDate = bookingDate;
    }

    public int    getBookingId()  { return bookingId;  }
    public int    getUserId()     { return userId;     }
    public int    getBusId()      { return busId;      }
    public int    getNumSeats()   { return numSeats;   }
    public double getTotalPrice() { return totalPrice; }
    public String getBookingDate(){ return bookingDate;}
}
