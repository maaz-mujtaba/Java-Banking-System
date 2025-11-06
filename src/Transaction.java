import java.sql.Timestamp;
public class Transaction {
    int txnId;
    int accountId;
    String accType;
    double amount;
    String description;
    Timestamp timestamp;
    public Transaction(int tId,int accId,String acT,double amt,String msg,Timestamp time){
        this.txnId = tId;
        this.accountId = accId;
        this.accType = acT;
        this.amount = amt;
        this.description = msg;
        this.timestamp = time;
    }
    public int getTxnId(){
        return txnId;
    }
    public int getAccountId(){
        return accountId;
    }
    public String getAccType(){
        return accType;
    }
    public double getAmount(){
        return amount;
    }
    public String getDesciption(){
        return description;
    }
    public Timestamp getTime(){
        return timestamp;
    }
    public String toString(){
        return "Transaction id : " + txnId+"\n"+
                "Account Id : " + accountId + "\n"+
                "Account Type : " + accType + "\n"+
                "Amount : Rs. " + amount;
    }
}
