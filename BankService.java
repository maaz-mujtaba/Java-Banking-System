
public class BankService {

    private UserDAO userDAO = new UserDAO();
    private BankAccountDAO accountDAO = new BankAccountDAO();

    public boolean login(String username, String password) {
        return userDAO.validateUser(username, password);
    }

    public double getBalance(String username) {
        return accountDAO.getAccount(username).getBalance();
    }

    public void deposit(String username, double amount) {
        accountDAO.getAccount(username).deposit(amount);
    }

    public boolean withdraw(String username, double amount) {
        return accountDAO.getAccount(username).withdraw(amount);
    }
}
