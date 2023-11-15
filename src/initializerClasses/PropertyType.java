package initializerClasses;

public abstract class PropertyType {

    public static double LOAN_INT_RATE = 0.06;
    public static double DEPOSIT_INT_RATE = 0.03;
    public static double PENALTY_INT_RATE = 0.01;

    public static double CHARGE_AMT_PERCENTAGE = 0.015;

    public static String [] ACCT_TYPE = {"LOAN", "DEPOSIT", "BANK"};

    public static String [] propertyName = {"DEPOSIT", "LOAN", "PENALTY", "TRANSACTON", "HANDLING", "LOW_BALANCE"};
    public static int getPropertyID(String propertyName)
    {
        // just to make it easier;
        return propertyName.indexOf(propertyName);
    }
}
