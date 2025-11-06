import java.time.LocalDateTime;

public class BankAccount {
    String name;
    String accType;
    long accountNumber;
    double balance;
    LocalDateTime createdAt;
    private boolean isActive;
    public BankAccount(String n,String acct,long acc,double bal){
        this.name = n;
        this.accType = acct;
        this.accountNumber = acc;
        this.balance = bal;
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
    }
    public void deposit(double amount){
        if(amount>0.0)
            balance += amount;
    }
    public void withdraw(double amount){
        if(amount>0.0)
            balance -= amount;
    }
    public boolean transfer(BankAccount receiver,double amount){
        if(amount<=0.0){
            System.out.println("Invalid Amount");
            return false;
        }
        if(this.balance<amount){
            System.out.println("Insufficient Amount");
            return false;
        }
        if(this.accountNumber == receiver.accountNumber){
            System.out.println("Can not send to the same Account");
            return false;
        }
        this.balance -= amount;
        receiver.balance += amount;
        System.out.println("Transfer successful: Rs."+amount+" from "+this.name+" to "+receiver.name);
        return true;
    }
    public double getBalance(){
        return this.balance;
    }
    public String getSummary(){
        return "Name : "+this.name+"\n"+
        "Account Type :"+this.accType+"\n"+
        "Account Number "+this.accountNumber+"\n"+
        "Balance : Rs."+String.format("%.2f",this.balance)+"\n"+
        "Created At : "+this.createdAt.toString();
    }
    public void closeAccount(){
        this.isActive = false;
        System.out.println("Account :"+this.accountNumber+" is now closed");
    }
}
