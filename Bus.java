public class Bus {
    int    busId;
    String busName;
    String busNumber;
    String fromLocation;
    String toLocation;
    String via;
    String travelDate;
    String departureTime;
    int    totalSeats;
    int    availableSeats;
    double price;

    public Bus(int busId, String busName, String busNumber, String fromLocation,
               String toLocation, String via, String travelDate, String departureTime,
               int totalSeats, int availableSeats, double price) {
        this.busId          = busId;
        this.busName        = busName;
        this.busNumber      = busNumber;
        this.fromLocation   = fromLocation;
        this.toLocation     = toLocation;
        this.via            = via;
        this.travelDate     = travelDate;
        this.departureTime  = departureTime;
        this.totalSeats     = totalSeats;
        this.availableSeats = availableSeats;
        this.price          = price;
    }

    public int    getBusId()          { return busId;          }
    public String getBusName()        { return busName;        }
    public String getBusNumber()      { return busNumber;      }
    public String getFromLocation()   { return fromLocation;   }
    public String getToLocation()     { return toLocation;     }
    public String getVia()            { return via;            }
    public String getTravelDate()     { return travelDate;     }
    public String getDepartureTime()  { return departureTime;  }
    public int    getTotalSeats()     { return totalSeats;     }
    public int    getAvailableSeats() { return availableSeats; }
    public double getPrice()          { return price;          }
}
