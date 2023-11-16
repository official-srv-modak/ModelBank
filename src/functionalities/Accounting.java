package functionalities;

import dynamicDb.DynamicDB;
import functionalities.logging.Logger;
import initializerClasses.Account;

public abstract class Accounting {
    public static void doAccounting(double amount, double fromAccountNumber, double toAccountNumber)
    {
        Account fromAccount = DynamicDB.getAccount(fromAccountNumber); // get from acc
        Account toAccount = DynamicDB.getAccount(toAccountNumber); // get to acc
        toAccount.setprinciple(toAccount.getprinciple() + amount); // add amt to TOaccount
        fromAccount.setprinciple(fromAccount.getprinciple() - amount); // subtract amt from FROMacc
        Logger.logSuccess("Accounting Operation", "\nDEBIT Account : "+fromAccount+"\n"+"CREDIT Account : "+toAccount+"\n");
    }
}
