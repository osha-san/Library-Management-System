import java.sql.*;

public class User {

    
    private int userId;
    private String username;
    private String password;
    private String name;
    private boolean isAdmin;


    public User (int userId, String username, String password, String name, boolean isAdmin) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getName(){
        return name;
    }

    public boolean isAdmin(){
        return isAdmin;
    }

    // Register a new user

    public static boolean register (String username, String password, String name, boolean isAdmin){
        String sql = "INSERT INTO users (username, password, name, is_admin) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setString(1,username);
                pstmt.setString(2, password);
                pstmt.setString(3, name);
                pstmt.setBoolean(4, isAdmin);

                pstmt.executeUpdate();

                System.out.println("User registered successfully");
                return true;
            }

            catch (SQLException e) {
                System.err.println("Error registering user: " + e.getMessage());
                return false;
            }
    }

    //Login user
    public static User login(String username, String password){
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setString(1, username);
                pstmt.setString(2, password);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()){
                    return new User(
                        rs.getInt("user_id"), 
                        rs.getString("username"), 
                        rs.getString("password"), 
                        rs.getString("name"), 
                        rs.getBoolean("is_admin"));
                }
                else {
                    return null;
                }
            }
            catch(SQLException e) {
                System.err.println("Error during login: " + e.getMessage());
                return null;
            }
    }
    //Check if admin exists

    public static boolean adminExists(){
        String sql = "SELECT COUNT(*) FROM users WHERE is_admin = true";

        try(Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

                if (rs.next()){
                    return rs.getInt(1) > 0;
                }
            }
        catch (SQLException e){
            System.err.println("Error checking admin: " + e.getMessage());
        }
        return false;
    }

    public void displayInfo(){
        System.out.println("User ID: " + userId);
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
        System.out.println("Role: " + (isAdmin ? "Admin" : "User "));
    }
}
