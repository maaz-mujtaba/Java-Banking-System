import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class BankAccountDAO {

    public static boolean createAccount(BankAccount account, int userId){
        String sql = "INSERT INTO accounts (user_id, accounttype, balance, created_at) VALUES(?,?,?,?)";
        try(Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, userId);
            stmt.setString(2, account.getAccType());
            stmt.setDouble(3, account.getBalance());
            stmt.setTimestamp(4, Timestamp.valueOf(account.createdAt));
            int rows = stmt.executeUpdate();
            if(rows==1){
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()){
                    account.accountNumber = rs.getLong(1);
                }
                return true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static BankAccount getAccountByUserId(int userId){
        String sql = "SELECT * FROM accounts WHERE user_id = ? AND isactve = true";
        try(Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                BankAccount acc= new BankAccount(
                        rs.getString("accounttype"),
                        rs.getLong("accountid"),
                        rs.getDouble("balance")
                );
                acc.createdAt = rs.getTimestamp("createdat").toLocalDateTime();
                return acc;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static boolean updateBalance(long accountNumber, double newBalance){
        String sql = "UPDATE accounts SET balance = ? WHERE accountid = ?";
        try(Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setDouble(1, newBalance);
            stmt.setLong(2, accountNumber);
            return stmt.executeUpdate()==1;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean closeAccount(long accountNumber){
        String sql = "UPDATE accounts SET isactive = false WHERE accountid = ?";
        try(Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, accountNumber);
            return stmt.executeUpdate()==1;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static BankAccount getAccountByNumber(long accountNumber){
        String sql = "SELECT * FROM accouts WHERE accountid = ?";
        try(Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                BankAccount acc= new BankAccount(
                        rs.getString("accounttype"),
                        rs.getLong("accountid"),
                        rs.getDouble("balance")
                );
                acc.createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                return acc;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static boolean isAccountActive(long accountNumber){
        String sql = "SELECT isactive FROM accounts WHERE accountid = ?";
        try(Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getBoolean("isactive");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static List<BankAccount> getAllAccountsByUser(int userId) {
        List<BankAccount> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE user_id = ? AND isactive = true";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BankAccount acc = new BankAccount(
                        rs.getString("accounttype"),
                        rs.getLong("accountid"),
                        rs.getDouble("balance")
                );
                acc.createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                accounts.add(acc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}
