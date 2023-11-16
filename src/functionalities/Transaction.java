package functionalities;

import dynamicDb.DynamicDB;
import dynamicDb.IntialiseDB;

import java.time.LocalDate;

public class Transaction {
    private double id = 0, amount = 0;
    private double fromAccountNumber, toAccountNumber;

    private LocalDate txnDate = LocalDate.now();
    private static int count = 0;

    public Transaction() {
        id = count+1;
        count++;
    }

    public Transaction(double amount, double fromAccountNumber, double toAccountNumber) {
        this();
        this.amount = amount;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
    }
    public void doTransaction(double amount, double fromAccountNumber, double toAccountNumber)
    {
        Accounting.doAccounting(amount, fromAccountNumber, toAccountNumber);
    }
    public void doTransaction()
    {
        Accounting.doAccounting(amount, fromAccountNumber, toAccountNumber);
    }

    public void deposit(double amount, double toAccountNumber)
    {
        this.fromAccountNumber = DynamicDB.accounts.get(IntialiseDB.maxNumber).getId(); // to get the BANK ACC
        this.toAccountNumber = toAccountNumber;// get it from user
        Accounting.doAccounting(amount, fromAccountNumber, toAccountNumber);
    }

    public void withdraw(double amount, double fromAccountNumber)
    {
        this.toAccountNumber = DynamicDB.accounts.get(IntialiseDB.maxNumber).getId(); // to get the BANK ACC
        this.fromAccountNumber = fromAccountNumber;// get it from user
        Accounting.doAccounting(amount, fromAccountNumber, toAccountNumber);
    }

    public double getFromAccountNumber() {
        return fromAccountNumber;
    }

//    public void setFromAccountNumber(double fromAccountNumber) {
//        this.fromAccountNumber = fromAccountNumber;
//    }

    public double getToAccountNumber() {
        return toAccountNumber;
    }

//    public void setToAccountNumber(double toAccountNumber) {
//        this.toAccountNumber = toAccountNumber;
//    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", transactionDate=" + txnDate +
                ", fromAccountNumber=" + fromAccountNumber +
                ", toAccountNumber=" + toAccountNumber +
                ", fromAccountPrinciple=" + DynamicDB.getAccount(fromAccountNumber).getprinciple() +
                ", toAccountPrinciple=" + DynamicDB.getAccount(toAccountNumber).getprinciple() +
                '}';
    }
}
