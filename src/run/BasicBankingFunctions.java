package run;

import dynamicDb.DynamicDB;
import functionalities.logging.Logger;
import functionalities.products.Deposit;
import functionalities.products.Loan;
import initializerClasses.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public abstract class BasicBankingFunctions {

    private static Scanner scan = new Scanner(System.in);
    public static Customer createCustomerPrompt()
    {
        scan.nextLine();
        System.out.println("Enter the First Name");
        String firstName = scan.nextLine();
        System.out.println("Enter the Last Name");
        String lastName = scan.nextLine();
        System.out.println("Enter the Address of the customer");
        String address = scan.nextLine();
        System.out.println("Enter the ID number for any national ID registered with the bank");
        String id = scan.nextLine();
        Customer customer = new Customer(firstName, lastName, address, id);

        Deposit deposit = createDepositAccountPrompt(customer, PropertyType.ACCT_TYPE[0]);
        DynamicDB.customers.add(customer);
        DynamicDB.accounts.add(deposit.getAccount());
        DynamicDB.deposits.add(deposit);
        System.out.println("Customer created : "+customer);
        return customer;
    }

    public static Customer createCustomer(String firstName, String lastName, String address, String idNumber, double initialBalance, String ACCT_TYPE)
    {
        Customer customer = new Customer(firstName, lastName, address, idNumber);

        Deposit deposit = createDepositAccount(customer, ACCT_TYPE, initialBalance);
        DynamicDB.customers.add(customer);
        DynamicDB.accounts.add(deposit.getAccount());
        DynamicDB.deposits.add(deposit);
        return customer;
    }

    public static Customer deleteCustomer()
    {
        System.out.println("Enter the customer id");
        int id = scan.nextInt();
        Customer customer = DynamicDB.getCustomer(id);
        if(customer != null)
        {
            customer.setActiveFlag(false);
        }
        Logger.logSuccess("BasicBankingFuntions", customer.toString());
        return customer;
    }

    public static Account deleteAccount()
    {
        System.out.println("Enter the account id");
        int id = scan.nextInt();
        Account account = DynamicDB.getAccount(id);
        if(account != null)
        {
            account.setActiveFlag(false);
        }
        Logger.logSuccess("BasicBankingFuntions", account.toString());
        return account;
    }
    public static ArrayList<Customer> createMultipleCustomersPrompt(int number)
    {
        ArrayList<Customer> customers = new ArrayList<>();
        for(int i = 0; i < number; i++)
        {
            System.out.println("Enter the details of Customer "+(i+1));
            customers.add(createCustomerPrompt());
        }
        DynamicDB.customers.addAll(customers);
        return customers;
    }

    public static Deposit createDepositAccountPrompt(Customer customer, String ACCT_TYPE)
    {
        Deposit deposit;
        System.out.println("Enter the intial bakance");
        double principle = scan.nextDouble();
        Interest interest = new Interest("DEPOSIT", PropertyType.DEPOSIT_INT_RATE);
        Charge charge = new Charge("DEPOSIT", PropertyType.CHARGE_AMT_PERCENTAGE);
        Account account;
        if(!ACCT_TYPE.isEmpty())
        {
            account= new Account(customer.getId(), ACCT_TYPE, principle);
        }
        else
        {
            account= new Account(customer.getId(), PropertyType.ACCT_TYPE[0], principle);
        }
        deposit = new Deposit(customer, account, interest, charge);
        customer.addDeposit(deposit);
        DynamicDB.deposits.add(deposit);
        Logger.logSuccess("BasicBankingFuctions", deposit.toString());
        return deposit;
    }

    public static Deposit createDepositAccount(Customer customer, String ACCT_TYPE, double principle)
    {
        Deposit deposit;
        Interest interest = new Interest("DEPOSIT", PropertyType.DEPOSIT_INT_RATE);
        Charge charge = new Charge("DEPOSIT", PropertyType.CHARGE_AMT_PERCENTAGE);
        Account account;
        if(!ACCT_TYPE.isEmpty())
        {
            account= new Account(customer.getId(), ACCT_TYPE, principle);
        }
        else
        {
            account= new Account(customer.getId(), PropertyType.ACCT_TYPE[0], principle);
        }
        deposit = new Deposit(customer, account, interest, charge);
        customer.addDeposit(deposit);
        DynamicDB.deposits.add(deposit);
        return deposit;
    }
    public static Loan createLoanPrompt(Customer customer, String ACCT_TYPE)
    {
        Loan loan;
        System.out.println("Enter the Sanctioned amount of loan");
        double sanctioned = scan.nextDouble();
        System.out.println("Enter the tenure of loan in years");
        double tenureYears = scan.nextDouble();
        System.out.println("Loan staring Today? Y/N ");
        scan.nextLine();
        String ch = scan.nextLine();
        Interest interest = new Interest("LOAN", PropertyType.DEPOSIT_INT_RATE);
        Charge charge = new Charge("LOAN", PropertyType.CHARGE_AMT_PERCENTAGE);
        Account account;

        if(!ACCT_TYPE.isEmpty())
        {
            account= new Account(customer.getId(), ACCT_TYPE, 0);
        }
        else
        {
            account= new Account(customer.getId(), PropertyType.ACCT_TYPE[0], 0);
        }
        if(ch.equalsIgnoreCase("Y"))
            loan = new Loan(customer, sanctioned, account, LocalDate.now(), interest, charge, tenureYears);
        else
        {
            System.out.println("Enter start Date in format YYYY-MM-DD");
            LocalDate date = LocalDate.parse(scan.nextLine());
            loan = new Loan(customer, sanctioned, account, date, interest, charge, tenureYears);
        }
        customer.addLoans(loan);
        DynamicDB.loans.add(loan);
        Logger.logSuccess("BasicBankingOperation", loan.toString());
        return loan;
    }

    public static void displayCustomerDetails(Customer customer)
    {
        System.out.println(customer);
    }
    public static void editCustomer(Customer customer)
    {
        System.out.println("Enter the parameter you want to edit?");
        String property = scan.nextLine();
        Customer.changeProperty(property, customer);
    }

    public static void seeAccount(double accountID)
    {
        Account account = DynamicDB.getAccount(accountID);

        System.out.println("ACCOUNT : ");
        System.out.println("Account ID : "+accountID);
        System.out.println("Account Balance : "+account.getprinciple());
        System.out.println("Account ACTIVE : "+account.isActiveFlag());
        System.out.println("Account Type : "+account.getACCT_TYPE());
        if(account.getACCT_TYPE().equalsIgnoreCase("DEPOSIT"))
        {
            Deposit deposit = DynamicDB.getDeposit(accountID);
            System.out.println("Account Interest Rate : "+deposit.getInterest());
            System.out.println("Account Charges Rate : "+deposit.getCharge());
            System.out.println("Deposit type : "+deposit.getdepositType());
            System.out.println("Customer : "+deposit.getCustomer());

        }
        else
        {
            Loan loan = DynamicDB.getLoan(accountID);
            System.out.println("Account Interest Rate : " + loan.getInterest()!=null?loan.getInterest():"NULL");
            System.out.println("Account Charges Rate : "+loan.getCharge()!=null?loan.getCharge():"NULL");
            System.out.println("Loan type : "+loan.getLoanType()!=null?loan.getLoanType():"NULL");
            System.out.println("Customer : "+loan.getCustomer()!=null?loan.getCustomer():"NULL");
        }
        System.out.println("Transactions done on Account : "+DynamicDB.getAllAccountTransactions(accountID));
    }

    public static void interestAccretion()
    {

    }


    public static void main(String[] args)
    {
//        Customer cus = new Customer();
//        Customer.changeProperty("firstname", cus);
    }
}
