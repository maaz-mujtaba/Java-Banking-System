
import java.util.HashMap;

public class UserDAO {

    private HashMap<String, User> users = new HashMap<>();

    public UserDAO() {
        // Default user
        users.put("admin", new User("admin", "1234"));
    }

    public boolean validateUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }
}
