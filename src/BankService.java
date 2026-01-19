import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
public class BankService {
    public static boolean openAccount(User user, String accountType, double initialDeposit)
    {
        BankAccount account = new BankAccount(accountType, 0, initialDeposit);
        account.createdAt = LocalDateTime.now();
        return BankAccountDAO.createAccount(account, user.getUserId());

    }

    public static boolean deposit(long accountNumber, double amount)
    {
        if (amount <= 0) return false;

        BankAccount acc = BankAccountDAO.getAccountByNumber(accountNumber);
        if(acc == null || !BankAccountDAO.isAccountActive(accountNumber)) return false;

        double newBalance = acc.getBalance() + amount;
        boolean updated = BankAccountDAO.updateBalance(accountNumber, newBalance);

        if (updated){
            Transaction txn = new Transaction(
                    0, (int)accountNumber,"withdraw", amount,
                    "Withdrawal of Rs. "+amount,
                    Timestamp.valueOf(LocalDateTime.now())
            );
            return updated;
        }

    }

    public static boolean transfer(long fromAccount, long toAccount, double amount)
    {
        if (amount <= 0) return false;

        BankAccount sender = BankAccountDAO.getAccountByNumber(fromAccount);
        BankAccount receiver = BankAccountDAO.getAcountByNumber(toAccount);

        if (sender == null || receiver == null) return false;
        if (!BankAccountDAO.isAccountActive(fromAccount) || !BankAccountDAO.isAccountActive(toAccount))
            return false; if (sender.getBalance() < amount) {
                System.out.println("Insufficient funds in sender account.");
                return false;
            }
        boolean debit = BankAccountDAO.updateBalance(fromAccount, sender.getBalance() - amount);
        boolean credit = BankAccountDAO.updateBalance(toAccount, receiver.getBalance() + amount);
        if (debit && credit) {
            Transaction txnOut = new Transaction( 0, (int) fromAccount,
                    "transfer-out", amount, "Transfer to account " + toAccount,
                    Timestamp.valueOf(LocalDateTime.now()) );

            TransactionDAO.recordTransaction(txnOut);
            Transaction txnIn = new Transaction( 0, (int) toAccount,
                    "transfer-in", amount, "Received from account " + fromAccount,
                    Timestamp.valueOf(LocalDateTime.now()) );
            TransactionDAO.recordTransaction(txnIn);
            return true;
        }
        return false;
    }
    public static boolean closeAccount(long accountNumber)
    {
        return BankAccountDAO.closeAccount(accountNumber);
    }

    public static List<BankAccount> getUserAccounts(int userId)
    {
        return BankAccountDAO.getAllAccountsByUser(userId);
    }
}