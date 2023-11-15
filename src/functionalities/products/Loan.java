package functionalities.products;

import initializerClasses.*;

import java.time.LocalDate;

public class Loan {
    private Customer customer;
    private Account account;
    private Interest interest;
    private Charge charge;
    private double tenureYears, tenureElapsed;

    private static final LocalDate startDate = LocalDate.now();
    private static LocalDate endDate = null;

    private double assumedAmount;

    private Interest penaltyInterest;

    private static boolean activeFlag = true;

    /*@Override
    public String toString() {
        return "Loan : " + account.getId()+"\n"+
                "Account = " + account +"\n"+
                "Loan Type = '" + account.getACCT_TYPE() + '\'' +"\n"+
                "Interest = " + interest +"\n"+
                "Charge = " + charge +"\n"+
                "Principle = " + account.getprinciple() +"\n"+
                "Tenure Years = " + tenureYears +"\n"+
                "Tenure Elapsed = " + tenureElapsed +"\n"+
                "Assumed Amount = " + assumedAmount +"\n"+
                "Penalty Interest = " + penaltyInterest+ "\n";
    }*/

    @Override
    public String toString() {
        return "Loan{" +
                "Account=" + account +
                ", interest=" + interest +
                ", charge=" + charge +
                ", tenureYears=" + tenureYears +
                ", tenureElapsed=" + tenureElapsed +
                ", assumedAmount=" + assumedAmount +
                ", penaltyInterest=" + penaltyInterest +
                '}';
    }

    public Loan(Customer customer, Account account, Interest interest, Charge charge, double tenureYears) {
        this.customer = customer;
        this.account = account;
        this.interest = interest;
        this.charge = charge;
        this.tenureYears = tenureYears;
        calculateAssumedAmount();
        calculatePenaltyInterest();
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

    public String getLoanType() {
        return account.getACCT_TYPE();
    }

    public void setLoanType(String loanType) {
        account.setACCT_TYPE(loanType);
        //this.loanType = loanType;
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
        //this.principle = principle;
    }

    public double getTenureMonths() {
        return tenureYears * 12;
    }

    public double getTenureYears() {
        return tenureYears;
    }

    public void setTenureYears(double tenureYears) {
        this.tenureYears = tenureYears;
    }

    private double calculateAssumedAmount()
    {
        double principle = account.getprinciple();
        double rate = interest.getRate();
        int n = 4;
        double preAm = principle*(1 + (rate/n));
        assumedAmount = Math.pow(preAm, tenureYears*n);
        return assumedAmount;
    }

    public double getAssumedAmount() {
        calculateAssumedAmount();
        return assumedAmount;
    }

    public Interest getPenaltyInterest() {
        return penaltyInterest;
    }

    public static LocalDate getEndDate() {
        return endDate;
    }

    public static void setEndDate(LocalDate endDate) {
        Loan.endDate = endDate;
        activeFlag = false;
    }

    public double getTenureElapsed()
    {
        if(activeFlag)
        {
            tenureElapsed = LocalDate.now().getYear() - startDate.getYear();
            return tenureElapsed;
        }
        else
        {
            tenureElapsed = endDate.getYear() - startDate.getYear();
            return tenureElapsed;
        }
    }

    public void calculatePenaltyInterest(){
        getTenureElapsed();
        if(tenureElapsed > tenureYears)
        {
            penaltyInterest.setRate(PropertyType.PENALTY_INT_RATE);
            Charge penaltyIntCharge = new Charge("PENALTY", PropertyType.CHARGE_AMT_PERCENTAGE);
            penaltyInterest.calculateInterest(account.getprinciple(), penaltyIntCharge);
        }
        else
        {
            penaltyInterest = new Interest("PENALTY", 0);
        }
    }

}
