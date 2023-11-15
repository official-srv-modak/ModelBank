package initializerClasses;

import functionalities.logging.Logger;

public class Interest {

    /*
    * Interest ID corresponds to an interest type.
    * In a perfect world it should be denoted by ENUM, but since it has not been taught, I am using the
    * other ways to do it.
    * So the Cheat sheet are as follows:
    * Int Number(Int)                    Interest Type (String)
    * 1                                  DEPOSIT
    * 2                                  LOAN
    * 3                                  PENALTY
    * All these are defined in an abstract class called PropertyType
    */

    private int interestID;

    private String interestType;
    private double rate;

    private static Charge charge;

    private double amount;

    private double closingBalance;

    public Interest(String interestType, double rate) {
        this.interestType = interestType;
        this.rate = rate;
        interestID = PropertyType.getPropertyID(interestType);
        charge = new Charge(interestType, rate);
    }

    public Interest(int interestID, double rate) {
        this.interestID = interestID;
        this.rate = rate;
        interestType = PropertyType.propertyName[interestID];
        charge = new Charge(interestID, rate);

    }

    public Interest(int interestID, String interestType, double rate) {
        this.interestID = interestID;
        this.interestType = interestType;
        this.rate = rate;
    }



    public int getInterestID() {
        return interestID;
    }

    public void setInterestID(int interestID) {
        this.interestID = interestID;
    }

    public String getInterestType() {
        return interestType;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double calculateInterest(double principle, Charge charge)
    {
        amount = principle + charge.getAmount(principle);
        return amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(double closingBalance) {
        this.closingBalance = closingBalance;
    }

    /*@Override
    public String toString() {
        return "Interest : " +"\n"+
                "Interest ID = " + interestID +"\n"+
                "Interest Type = '" + interestType + '\'' +"\n"+
                "Interest Rate = " + rate +"\n"+
                "Amount=" + amount + "\n";
    }*/

    @Override
    public String toString() {
        return "Interest{" +
                "interestID=" + interestID +
                ", interestType='" + interestType + '\'' +
                ", rate=" + rate +
                ", amount=" + amount +
                ", closingBalance=" + closingBalance +
                '}';
    }
}
