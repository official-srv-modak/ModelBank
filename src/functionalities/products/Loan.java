package functionalities.products;

import dynamicDb.IntialiseDB;
import functionalities.Transaction;
import functionalities.logging.Logger;
import initializerClasses.*;

import java.time.LocalDate;

public class Loan {
    private Customer customer;

    private double sanctionedAmount;
    private double borrowedAmount;
    private Account account;
    private Interest interest;
    private Charge charge;
    private double tenureYears, tenureElapsed;

    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = null;

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

    public Loan(Customer customer, double santionedAmount, Account account, LocalDate startDate, Interest interest, Charge charge, double tenureYears) {
        this.customer = customer;
        this.sanctionedAmount = santionedAmount;
        this.account = account;
        this.startDate = startDate;
        this.interest = interest;
        this.charge = charge;
        this.tenureYears = tenureYears;
        this.borrowedAmount = account.getprinciple();
        doLoanAccounting();
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

    public void setTenureElapsed() {
        this.tenureElapsed = LocalDate.now().getYear() - startDate.getYear();
        String str = "";
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
        double interestAmount = account.getprinciple() * interest.getRate() * tenureYears /100;
        assumedAmount = interestAmount + account.getprinciple();
        return assumedAmount;
    }

    private double calculateAmountTillDate()
    {
        double amount = account.getprinciple() * interest.getRate() * tenureElapsed /100;
        interest.setAmount(amount);
        interest.setClosingBalance(account.getprinciple()+amount);
        return amount;
    }

    public double getAssumedAmount() {
        calculateAssumedAmount();
        return assumedAmount;
    }

    public Interest getPenaltyInterest() {
        return penaltyInterest;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        activeFlag = false;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        doLoanAccounting();
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

    public double calculatePenaltyInterest(){
        getTenureElapsed();
        if(tenureElapsed > tenureYears)
        {
            penaltyInterest = new Interest("PENALTY", PropertyType.PENALTY_INT_RATE);
            penaltyInterest.setRate(PropertyType.PENALTY_INT_RATE);
            Charge penaltyIntCharge = new Charge("PENALTY", PropertyType.CHARGE_AMT_PERCENTAGE);
            penaltyInterest.calculateInterest(account.getprinciple(), penaltyIntCharge);
            penaltyInterest.setClosingBalance(penaltyInterest.getAmount());
        }
        else
        {
            penaltyInterest = new Interest("PENALTY", 0);
            penaltyInterest.setClosingBalance(penaltyInterest.getAmount());
        }
        return penaltyInterest.getAmount();
    }
    public void setTotalBalance()
    {
        account.setprinciple(account.getprinciple()+calculateAmountTillDate()+calculatePenaltyInterest());
    }

    public double getSanctionedAmount() {
        return sanctionedAmount;
    }

    public void setSanctionedAmount(double sanctionedAmount) {
        this.sanctionedAmount = sanctionedAmount;
    }

    public double getBorrowedAmount() {
        return borrowedAmount;
    }

    public void setBorrowedAmount(double borrowedAmount) {
        this.borrowedAmount = borrowedAmount;
    }

    public void disburseLoan(double disburseAmount, LocalDate date)
    {
        if(disburseAmount <= sanctionedAmount)
        {
//            this.borrowedAmount = disburseAmount;
//            this.setPrinciple(this.getPrinciple()+disburseAmount);

            disburseAccounting(disburseAmount, date);
            Logger.logSuccess("Loan", this.toString());

        }
    }
    public void doLoanAccounting()
    {
        setTenureElapsed();
        calculateAmountTillDate();
        calculateAssumedAmount();
        calculatePenaltyInterest();
        setTotalBalance();
    }
    public void disburseAccounting(double amt, LocalDate date)
    {
        setStartDate(date);
        Transaction t1 = new Transaction();
        t1.doTransaction(amt, IntialiseDB.bankAccID,account.getId());
        setTenureElapsed();
        calculateAmountTillDate();
        calculateAssumedAmount();
        calculatePenaltyInterest();
        setTotalBalance();
    }

    public void repayLoan(double amt, LocalDate date, double fromAcc)
    {
        if(this.account.getprinciple() >= amt)
        {
            Transaction t1 = new Transaction();
            t1.doTransaction(amt, fromAcc,account.getId());
            setTenureElapsed();
            calculateAmountTillDate();
            calculateAssumedAmount();
            calculatePenaltyInterest();
            setTotalBalance();

            if(account.getprinciple()==0)
            {
                this.setEndDate(date);
            }
            Logger.logSuccess("Loan", this.toString());
        }

    }
    @Override
    public String toString() {
        return "Loan{" +
                "Account=" + account +
                ", sanctioned amount="+ sanctionedAmount +
                ", disbursed amount="+ borrowedAmount +
                ", Start Date="+ startDate +
                ", interest=" + interest +
                ", charge=" + charge +
                ", tenureYears=" + tenureYears +
                ", tenureElapsed=" + tenureElapsed +
                ", assumedAmount=" + assumedAmount +
                ", penaltyInterest=" + penaltyInterest +
                '}';
    }
}
