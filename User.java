public class User {
    int    userId;
    String name;
    String email;
    String phone;
    String password;

    public User(int userId, String name, String email, String phone, String password) {
        this.userId   = userId;
        this.name     = name;
        this.email    = email;
        this.phone    = phone;
        this.password = password;
    }

    public int    getUserId()  { return userId;   }
    public String getName()    { return name;     }
    public String getEmail()   { return email;    }
    public String getPhone()   { return phone;    }
    public String getPassword(){ return password; }
}
