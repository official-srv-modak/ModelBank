package initializerClasses;

import functionalities.logging.Logger;
import functionalities.products.Deposit;
import functionalities.products.Loan;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer {
    private double id = 0;

    private static double count = 0;
    public static int maxLoans = 10;
    public static int maxDeposits = 5;
    private String firstName, lastName, address, idNumber;

    private boolean activeFlag = true;

    ArrayList<Account> accounts = new ArrayList<>();
   // private Account[] accounts = new Account[1];

    private ArrayList<Loan> loans = new ArrayList<>();
    private ArrayList<Deposit> deposits = new ArrayList<>();

    public Customer() {
        id = count+1;
        count++;
    }

    public Customer(String firstName, String lastName, String address, String idNumber) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.idNumber = idNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

//    public void addAccount(Account account){
//        if(accounts.size() < maxAccountNumber)
//        {
//           accounts.add(account);
//            //accounts[accounts.length] = account;
//        }
//        else {
//            Logger.log(this.getClass().getSimpleName(), "MAX account numbers of "+maxAccountNumber+" reached");
//        }
//    }

    public static int getMaxLoans() {
        return maxLoans;
    }

    public static void setMaxLoans(int maxLoans) {
        Customer.maxLoans = maxLoans;
    }

    public void addLoans(Loan loan){
        if(loans.size() < maxLoans)
        {
            loans.add(loan);
            accounts.add(loan.getAccount());
            //loans[loans.length] = loan;
        }
        else {
            Logger.logError(this.getClass().getSimpleName(), "MAX loan numbers of "+maxLoans+" reached");
        }
    }

    public static int getMaxDeposits() {
        return maxDeposits;
    }

    public static void setMaxDeposits(int maxDeposits) {
        Customer.maxDeposits = maxDeposits;
    }

    public void addDeposit(Deposit deposit){
        if(deposits.size() < maxDeposits)
        {
            deposits.add(deposit);
            accounts.add(deposit.getAccount());
           // deposits[deposits.length] = deposit;
        }
        else {
            Logger.logError(this.getClass().getSimpleName(), "MAX deposit numbers of "+maxLoans+" reached");
        }
    }

    public double getId() {
        return id;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    static Scanner scan = new Scanner(System.in);
    public static void changeProperty(String propName, Object myObject)
    {
        Class class1 = Customer.class;

        Field[] fields = class1.getDeclaredFields();
        for (Field field : fields) {
            if(field.getName().equalsIgnoreCase(propName))
            {
                System.out.println("Change "+field.getName()+"? Enter new value : ");
                // String value = scan.nextLine();
                String type = field.getType().getSimpleName();
                try{
                    if (field.getType() == String.class) {
                        // For String fields
                        field.set(myObject, scan.nextLine());
                    } else if (field.getType() == int.class) {
                        // For int fields
                        field.set(myObject, scan.nextInt());
                    } else if (field.getType() == double.class) {
                        // For double fields
                        field.set(myObject, scan.nextDouble());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    public static Customer searchCustomerPropValues(String predicate, ArrayList<Customer> myObjects)
    {
        Class class1 = Customer.class;
        Customer obj = new Customer();
        Field[] fields = class1.getDeclaredFields();
        for(Object myObject : myObjects) {
            for (Field field : fields) {
                try {
                    if (field.get(myObject).toString().contains(predicate)) {
                        obj = (Customer) myObject;
                        break;
                    }

                } catch (Exception e) {
                    e.getSuppressed();
                }
            }
        }
            return obj;
    }

    @Override
    public String toString() {
        return "Customer ID = "+id+ "\n"+
                "First Name = " + firstName +"\n"+
                "Last Name = " + lastName  +"\n"+
                "Address = " + address  +"\n"+
                "Document ID Number = " + idNumber  +"\n"+
                "is Active? = " + activeFlag +"\n"+
                "accounts = " + accounts +"\n"+
                "loans = " + loans +"\n"+
                "deposits = " + deposits + "\n";
    }

    /*@Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", activeFlag=" + activeFlag +
                ", accounts=" + accounts +
                ", loans=" + loans +
                ", deposits=" + deposits +
                '}';
    }*/
}
