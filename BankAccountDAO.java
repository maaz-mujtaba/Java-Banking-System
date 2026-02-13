
import java.util.HashMap;

public class BankAccountDAO {

    private HashMap<String, BankAccount> accounts = new HashMap<>();

    public BankAccountDAO() {
        accounts.put("admin", new BankAccount("admin", 10000));
    }

    public BankAccount getAccount(String username) {
        return accounts.get(username);
    }
}

