package functionalities.products;

import initializerClasses.*;

import java.time.LocalDate;

public class Deposit {
    private Customer customer;
    private Account account;
    private Interest interest;
    private Charge charge;
    private static final LocalDate startDate = LocalDate.now();
    private static LocalDate endDate = null;

    private double assumedAmount;

    public Deposit(Customer customer, Account account, Interest interest, Charge charge) {
        this.customer = customer;
        this.account = account;
        this.interest = interest;
        this.charge = charge;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getdepositType() {
        return account.getACCT_TYPE();
    }

    public void setdepositType(String depositType) {
        account.setACCT_TYPE(depositType);
    }

    public Interest getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    public double getPrinciple() {
        return account.getprinciple();
    }

    public void setPrinciple(double principle) {
        account.setprinciple(principle);
    }


    public static LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        Deposit.endDate = endDate;
        account.setActiveFlag(false);
    }


    /*@Override
    public String toString() {
        return "Deposit : "+ account.getId() +"\n"+
                "Account = " + account +"\n"+
                "Deposit type = '" + account.getACCT_TYPE() + '\'' +"\n"+
                "Interest = " + interest +"\n"+
                "Charge = " + charge +"\n"+
                "Principle = " + account.getprinciple() +"\n"+
                "Assumed Amount = " + assumedAmount+ "\n";
    }*/

    @Override
    public String toString() {
        return "Deposit{" +
                "Account=" + account +
                ", interest=" + interest +
                ", charge=" + charge +
                ", assumedAmount=" + assumedAmount +
                '}';
    }
}
