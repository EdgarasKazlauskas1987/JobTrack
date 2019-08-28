package Model;


public class User {
    
    private int userId;
    private String fullName;
    private String userName;
    private String password;

    public String getFullName() {
        return fullName;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public User( int userId, String fullName, String userName, String password) {
        this.userId = userId;
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
    }
}
