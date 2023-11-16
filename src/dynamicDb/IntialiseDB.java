package dynamicDb;

import functionalities.products.Deposit;
import functionalities.products.Loan;
import initializerClasses.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public abstract class IntialiseDB {

    public static int maxNumber = 100;

    public static double bankAccID = maxNumber+1;
    private static String[] firstName = {"Sourav", "Manav", "John", "Mark", "Meena", "Sneha", "Rohit"},
        lastName = {"Modak", "Smith", "Doe", "Patel", "Singhania", "Kumar", "Singh"};

    public static int generateRandom(int min, int max)
    {
        Random rand = new Random();
        int randomNum = rand.nextInt(max - min + 1) + min;
        return randomNum;
    }
    public static ArrayList<Customer> createCustomers()
    {
        ArrayList<Customer> arrayList = new ArrayList<>();
        for(int i = 0; i < maxNumber; i++)
        {
            Customer customer = new Customer(firstName[generateRandom(0,firstName.length-1)], lastName[generateRandom(0,lastName.length-1)], generateRandomString(1), generateRandomString(2));
            arrayList.add(customer);
        }
        Customer bank = new Customer("Bank", "Bank", generateRandomString(1), generateRandomString(2));
        arrayList.add(bank);
        return arrayList;
    }

    public static ArrayList<Deposit> createDeposits()
    {
        ArrayList<Deposit> arrayList = new ArrayList<>();
        for(int i = 0; i < maxNumber; i++)
        {
            Customer customer = DynamicDB.customers.get(i);
            Account account = new Account(customer.getId(), "DEPOSIT", generateRandom(1000, 10000));
            Deposit deposit = new Deposit(customer, account, new Interest("DEPOSIT", PropertyType.DEPOSIT_INT_RATE), new Charge("DEPOSIT", PropertyType.CHARGE_AMT_PERCENTAGE));
            customer.addDeposit(deposit);
            arrayList.add(deposit);
            DynamicDB.accounts.add(account);
        }
        Customer customer = DynamicDB.getCustomer(maxNumber+1);
        Account bankTill = new Account(maxNumber+1, "BANK", 100000000);
        Deposit till = new Deposit(DynamicDB.getCustomer(maxNumber+1), bankTill, new Interest("DEPOSIT", PropertyType.DEPOSIT_INT_RATE), new Charge("DEPOSIT", PropertyType.CHARGE_AMT_PERCENTAGE));
        customer.addDeposit(till);
        DynamicDB.accounts.add(bankTill);
        return arrayList;
    }

    public static ArrayList<Loan> createLoans()
    {
        ArrayList<Loan> arrayList = new ArrayList<>();
        for(int i = 0; i < maxNumber; i++)
        {
            Customer customer = DynamicDB.customers.get(i);
            Account account = new Account(customer.getId(), "LOAN", 0);
            int year = generateRandom(1970, LocalDate.now().getYear()-3);
            int m = generateRandom(1, 12);
            int date = generateRandom(1, 28);
            String month = String.valueOf(m);
            String day = String.valueOf(date);
            if(m < 10)
                month = "0"+m;
            if(date < 10)
                day = "0"+date;
            LocalDate startDate = LocalDate.parse(year+"-"+month+"-"+day);
            Loan loan = new Loan(customer, generateRandom(1000, 10000), account, startDate, new Interest("LOAN", PropertyType.LOAN_INT_RATE), new Charge("LOAN", PropertyType.CHARGE_AMT_PERCENTAGE), generateRandom(1,12));
            customer.addLoans(loan);
            arrayList.add(loan);
            DynamicDB.accounts.add(account);
        }
        return arrayList;
    }

    public static String generateRandomString(int mode) {
        String randomChars = "";
        switch (mode)
        {
            case 1 :
                randomChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890            ";
                break;
            case 2:
                randomChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                break;
        }
        StringBuilder randomBuilder = new StringBuilder();
        Random rnd = new Random();
        while (randomBuilder.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * randomChars.length());
            randomBuilder.append(randomChars.charAt(index));
        }
        String output = randomBuilder.toString();
        return output;
    }

    public static void intialise()
    {
        DynamicDB.customers = createCustomers();
        DynamicDB.deposits = createDeposits();
        DynamicDB.loans = createLoans();
    }



    public static void main(String[] args)
    {
        for(int i =0; i< 100; i++)
        {
            System.out.println(generateRandom(0, 10));
        }
    }

}
