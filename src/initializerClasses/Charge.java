package initializerClasses;

import functionalities.logging.Logger;

public class Charge {

    /*
     * Charge ID corresponds to a charge type.
     * In a perfect world it should be denoted by ENUM, but since it has not been taught, I am using the
     * other ways to do it.
     * So the Cheat sheet are as follows:
     * Charge Number(Int)                    Charge Type (String)
     * 1                                     DEPOSIT_CHARGE
     * 2                                     LOAN_CHARGE
     * 3                                     PENALTY_CHARGE
     * 4                                     TRANSACTION_CHARGE
     * 5                                     HANDLING_CHARGE
     * 6                                     LOW_BALANCE_CHARGE
     *
     * All these are defined in an abstract class called PropertyType
     */

    private int chargeID;

    private String chargeType;

    private static double amountPercentage = 0.015;

    private double amount;

    public Charge(int chargeID, String chargeType, double amountPercentage) {
        this.chargeID = chargeID;
        this.chargeType = chargeType;
        this.amountPercentage = amountPercentage;
    }

    public Charge(int chargeID, double amountPercentage) {
        this.chargeID = chargeID;
        this.amountPercentage = amountPercentage;
        chargeType = PropertyType.propertyName[chargeID];
    }

    public Charge(String chargeType, double amountPercentage) {
        this.chargeType = chargeType;
        this.amountPercentage = amountPercentage;
        chargeID = PropertyType.getPropertyID(chargeType);
    }

    public int getChargeID() {
        return chargeID;
    }

    public void setChargeID(int chargeID) {
        this.chargeID = chargeID;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public static double getAmountPercentage() {
        return amountPercentage;
    }

    public static void setAmountPercentage(double amountPercentage) {
        Charge.amountPercentage = amountPercentage;
    }

    public double getAmount(double principle) {
        amount = principle * amountPercentage;
        return amount;
    }

    /*@Override
    public String toString() {
        return "Charge : " +"\n"+
                "Charge ID = " + chargeID +"\n"+
                "Charge Type = '" + chargeType + '\'' +"\n"+
                "Amount = " + amount + "\n";
    }*/

    @Override
    public String toString() {
        return "Charge{" +
                "chargeID=" + chargeID +
                ", chargeType='" + chargeType + '\'' +
                ", amount=" + amount +
                '}';
    }
}
