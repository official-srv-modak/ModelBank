package functionalities;

import dynamicDb.DynamicDB;
import functionalities.logging.Logger;
import initializerClasses.Account;

public abstract class Accounting {
    public static void doAccounting(double amount, double fromAccountNumber, double toAccountNumber)
    {
        Account fromAccount = DynamicDB.getAccount(fromAccountNumber);
        Account toAccount = DynamicDB.getAccount(toAccountNumber);
        toAccount.setprinciple(toAccount.getprinciple() + amount);
        fromAccount.setprinciple(fromAccount.getprinciple() - amount);
        Logger.logSuccess("Accounting Operation", "\nCREDIT Account : "+fromAccount+"\n"+"DEBIT Account : "+toAccount+"\n");
    }
}
