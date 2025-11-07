import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.security.*;
public class UserDAO {
    public static boolean createUser(User user){
        String sql = "INSERT INTO users (username, password, role) VALUES(?,?,?)";
        try(
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, user.getName());
            stmt.setString(2, hashPassword(user.getPassword()));
            stmt.setString(3, user.getRole());
            return stmt.executeUpdate()==1;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static User authenticateUser(String username, String password){
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try(
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, hashPassword(password));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }else{
                System.out.println("Failed login attempt for username : " + username + " at " + new java.util.Date());
            }
        }
            catch(SQLException e){
                e.printStackTrace();
        }
            return null;
    }
    public static User getUserById(int userId){
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                    );
                }
            }catch(SQLException e){
                    e.printStackTrace();
        }
        return null;
    }
    public static List<User> getAllUsers(){
        List <User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try(Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                users.add(
                        new User
                                (rs.getInt("user_id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role"))
                );
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return users;
    }
    public static boolean isUserNameTaken(String username){
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try(Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1)>0;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static String hashPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(byte b : hash){
                sb.append(String.format("%02x",b));
            }
            return sb.toString();
        }
        catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
    public static boolean deleteUser(int userId){
        String sql = "DELETE FROM users WHERE user_id = ?";
        try(Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,userId);
            return stmt.executeUpdate()==1;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean updatePassword(int userId, String newPassword){
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";
        try(Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,hashPassword(newPassword));
            stmt.setInt(2,userId);
            return stmt.executeUpdate()==1;
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}


