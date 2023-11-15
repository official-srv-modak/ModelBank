package run;

import dynamicDb.DynamicDB;
import dynamicDb.IntialiseDB;
import functionalities.Transaction;
import functionalities.products.Deposit;
import functionalities.products.Loan;
import initializerClasses.*;

import java.util.Scanner;

import static run.BasicBankingFunctions.deleteAccount;
import static run.BasicBankingFunctions.seeAccount;

public class Bank {
    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args)
    {
        intialise();
    }

    private static void intialise() {
//        Customer c1 = new Customer("Sourav", "Modak", "North York M3H 0E3", "XNBC129");
//        Customer c2 = new Customer("Manav", "Patel", "Burlington M4H 1F9", "CBIA128");
//
////        c1.addDeposit(new Deposit(new Account(c1.getId(), PropertyType.ACCT_TYPE[0], 2000), PropertyType.ACCT_TYPE[0], new Interest("DEPOSIT", PropertyType.DEPOSIT_INT_RATE), new Charge("DEPOSIT", PropertyType.CHARGE_AMT_PERCENTAGE), 2000)));
////        c2.addAccount(new Account(c2.getId(), PropertyType.ACCT_TYPE[0], 5000));
//        Customer c3 = BasicBankingFunctions.createCustomerPrompt();
//        BasicBankingFunctions.createLoanPrompt(c3, "");
//        BasicBankingFunctions.displayCustomerDetails(c1);
//        BasicBankingFunctions.displayCustomerDetails(c2);
//        BasicBankingFunctions.displayCustomerDetails(c3);

        intialiseBankingMenu();

    }
    public static void intialiseBankingMenu()
    {
        IntialiseDB.intialise();

        while(true)
        {
            System.out.println("1. CUSTOMER Operations");
            System.out.println("2. ACCOUNT Operations");
            System.out.println("3. TRANSACTION Operations");
            System.out.println("4. EXIT");
            String temp = scan.nextLine();
            int ch = Integer.parseInt(temp.trim().isBlank()?"0":temp.trim()); // because sometimes the scanner is skipping some input prompt stops
            switch(ch)
            {
                case 1:
                    customerOperationsMenu();
                    break;
                case 2:
                    accountOperationsMenu();
                    break;
                case 3:
                    transactionOperationsMenu();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("INVALID OPTION!!! Try again");
                    break;
            }
        }

    }
    public static void customerOperationsMenu()
    {
        System.out.println("1. CREATE Customer");
        System.out.println("2. MODIFY Customer");
        System.out.println("3. DELETE Customer");
        System.out.println("4. SEE Customer details");
        System.out.println("5. SEE all Customers details");
        String temp = scan.nextLine();
        int ch = Integer.parseInt(temp.trim().isBlank()?"0":temp.trim()); // because sometimes the scanner is skipping some input prompt stops
        switch (ch)
        {
            case 1:
                Customer customer = BasicBankingFunctions.createCustomerPrompt();
                addCustomersToDb(customer);
                break;
            case 2:
                break;
            case 3:
                customer = BasicBankingFunctions.deleteCustomer();
                addCustomersToDb(customer);
                break;
            case 4:
//                System.out.println("Enter id of the customer : ");
//                String temp = scan.nextLine();
//                int ch = Integer.parseInt(temp.trim().isBlank()?"0":temp.trim()); // because sometimes the scanner is skipping some input prompt stops
//                Customer cus = DynamicDB.customers.get(id-1);
//                BasicBankingFunctions.displayCustomerDetails(cus);

                System.out.println("Enter any property value of customer : ");
                String pred = scan.nextLine();
                System.out.println(Customer.searchCustomerPropValues(pred, DynamicDB.customers));
                break;
            case 5:
                for(Customer cus : DynamicDB.customers)
                    System.out.println(cus);
                break;
            default:
                System.out.println("INVALID OPTION!!! TRY AGAIN");
        }

    }
    public static void accountOperationsMenu()
    {
        System.out.println("1. Loan Operation");
        System.out.println("2. Deposit Operation");
        System.out.println("3. See specific Account details");
        System.out.println("4. SEE all Account details");
        System.out.println("5. SEE all Loan details");
        System.out.println("6. SEE all Deposit details");
        String temp = scan.nextLine();
        int ch = Integer.parseInt(temp.trim().isBlank()?"0":temp.trim()); // because sometimes the scanner is skipping some input prompt stops
        switch (ch)
        {
            case 1 :
                loanOperations();
                break;
            case 2:
                depositAccountOperations();
                break;
            case 3:
                System.out.println("Enter account number : ");
                seeAccount(scan.nextDouble());
                break;
            case 4:
                for(Account acc : DynamicDB.accounts)
                    System.out.println(acc);
                break;
            case 5:
                for(Loan loan : DynamicDB.loans)
                    System.out.println(loan);
                break;
            case 6:
                for(Deposit dep : DynamicDB.deposits)
                    System.out.println(dep);
                break;
            default:
                System.out.println("INVALID OPTION!!! TRY AGAIN");
        }
    }

    private static void depositAccountOperations() {
        System.out.println("1. CREATE Deposit");
        System.out.println("2. MODIFY Deposit");
        System.out.println("3. CLOSE Deposit");
        System.out.println("4. SEE Deposit details");
        System.out.println("5. SEE all deposit details");
        String temp = scan.nextLine();
        int ch = Integer.parseInt(temp.trim().isBlank()?"0":temp.trim()); // because sometimes the scanner is skipping some input prompt stops
        switch (ch)
        {
            case 1:
                System.out.println("Enter the Customer id");
                int id = Integer.parseInt(scan.nextLine().trim()); // because sometimes the scanner is skipping some input prompt stops
                Customer customer = DynamicDB.getCustomer(id);
                if(customer != null)
                    BasicBankingFunctions.createDepositAccountPrompt(customer, "DEPOSIT");
                else
                    System.out.println("INVALID CUSTOMER!!! TRY AGAIN");
                break;
            case 2:
                break;
            case 3:
                deleteAccount();
                break;
            case 4:
                System.out.println("Enter account number : ");
                seeAccount(scan.nextDouble());
                break;
            case 5:
                for(Deposit dep : DynamicDB.deposits)
                    System.out.println(dep);
                break;
            default:
                System.out.println("INVALID OPTION!!! TRY AGAIN");

        }
    }

    public static void transactionOperationsMenu()
    {
        System.out.println("1. DEPOSIT");
        System.out.println("2. WITHDRAW");
        System.out.println("3. FUND TRANSFER");
        System.out.println("4. CHECK BALANCE");
        System.out.println("5. LOAN REPAYMENT");
        System.out.println("6. LOAN DISBURSE");
        System.out.println("7. Show ALL transactions");
        String temp = scan.nextLine();
        int ch = Integer.parseInt(temp.trim().isBlank()?"0":temp.trim()); // because sometimes the scanner is skipping some input prompt stops

        /*String temp = scan.nextLine();
        int ch = Integer.parseInt(temp.trim().isBlank()?"0":temp.trim()); // because sometimes the scanner is skipping some input prompt stops*/
        switch (ch)
        {
            case 1:
                depositOperations();
                break;
            case 2:
                withdrawOperations();
                break;
            case 3:
                fundsTransfer();
                break;
            case 4:
                System.out.println("Enter account number : ");
                seeAccount(scan.nextDouble());
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                for(Transaction txn : DynamicDB.transactions)
                    System.out.println(txn);
                break;
            default:
                System.out.println("INVALID OPTION!!! TRY AGAIN");
        }
    }

    public static void loanOperations()
    {
        System.out.println("1. CREATE Loan");
        System.out.println("2. MODIFY Loan");
        System.out.println("3. CLOSE Loan");
        System.out.println("4. SEE Loan details");
        System.out.println("5. SEE all Loans details");
        String temp = scan.nextLine();
        int ch = Integer.parseInt(temp.trim().isBlank()?"0":temp.trim()); // because sometimes the scanner is skipping some input prompt stops
        switch (ch)
        {
            case 1:
                System.out.println("Enter the borrowing Customer id");
                int id = Integer.parseInt(scan.nextLine().trim()); // because sometimes the scanner is skipping some input prompt stops
                Customer customer = DynamicDB.getCustomer(id);
                if(customer != null)
                    BasicBankingFunctions.createLoanPrompt(customer, "");
                else
                    System.out.println("INVALID CUSTOMER!!! TRY AGAIN");
                break;
            case 2:
                break;
            case 3:
                deleteAccount();
                break;
            case 4:
                System.out.println("Enter account number : ");
                seeAccount(scan.nextDouble());
                break;
            case 5:
                for(Loan loan : DynamicDB.loans)
                    System.out.println(loan);
                break;
            default:
                System.out.println("INVALID OPTION!!! TRY AGAIN");

        }
    }

    public static String scanString()
    {
        String userInput = scan.nextLine();

        while (userInput.trim().isEmpty()) {
            userInput = scan.nextLine();
        }

        return userInput;
    }

    private static void addCustomersToDb(Customer customer)
    {
        if(DynamicDB.customers.contains(customer))
            DynamicDB.customers.set(DynamicDB.customers.indexOf(customer), customer);
        else
            DynamicDB.customers.add(customer);
    }
    public static void depositOperations()
    {
        System.out.println("Enter the CREDIT account number");
        double toAccountNumber = scan.nextDouble();
        System.out.println("Enter the amount");
        double amount = scan.nextDouble();
        Transaction t1 = new Transaction();
        t1.deposit(amount, toAccountNumber);
        DynamicDB.transactions.add(t1);
    }

    public static void withdrawOperations()
    {
        System.out.println("Enter the DEBIT account number");
        double fromAccountNumber = scan.nextDouble();
        System.out.println("Enter the Amount");
        double amount = scan.nextDouble();
        Transaction t2 = new Transaction();
        t2.withdraw(amount, fromAccountNumber);
        DynamicDB.transactions.add(t2);
    }

    public static void fundsTransfer()
    {
        System.out.println("Enter the CREDIT account number");
        double toAccountNumber = scan.nextDouble();
        System.out.println("Enter the DEBIT account number");
        double fromAccountNumber = scan.nextDouble();
        System.out.println("Enter the Amount");
        double amount = scan.nextDouble();
        Transaction t1 = new Transaction(amount, fromAccountNumber, toAccountNumber);
        t1.doTransaction();
        DynamicDB.transactions.add(t1);
    }
}
