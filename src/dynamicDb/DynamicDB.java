package dynamicDb;

import functionalities.Transaction;
import functionalities.products.Deposit;
import functionalities.products.Loan;
import initializerClasses.Account;
import initializerClasses.Customer;

import java.util.ArrayList;

public abstract class DynamicDB {
    public static int maxSize = 10000000;
    public static ArrayList<Customer> customers = new ArrayList<>(maxSize);

    public static ArrayList<Account> accounts = new ArrayList<>(maxSize);
    public static ArrayList<Deposit> deposits = new ArrayList<>(maxSize);

    public static ArrayList<Loan> loans = new ArrayList<>(maxSize);

    public static ArrayList<Transaction> transactions = new ArrayList<>(maxSize);



    public static Customer getCustomer(int id)
    {
        for(Customer customer : customers)
        {
            if (customer.getId() == id)
                return customer;
        }
        return null;
    }

    public static Account getAccount(double id)
    {
        for(Account account : accounts)
        {
            if (account.getId() == id)
                return account;
        }
        return null;
    }
}
