package initializerClasses;

public class Account {
    private double id = 0;
    private static double count = 0;

    private double customerId;

    private String ACCT_TYPE;

    private double principle;

    private boolean activeFlag = true;

    public Account() {
        id = count+1;
        count++;
    }

    public Account(double customerId, String ACCT_TYPE, double principle) {
        this();
        this.customerId = customerId;
        this.ACCT_TYPE = ACCT_TYPE;
        this.principle = principle;
    }

    public String getACCT_TYPE() {
        return ACCT_TYPE;
    }

    public void setACCT_TYPE(String ACCT_TYPE) {
        this.ACCT_TYPE = ACCT_TYPE;
    }

    public double getprinciple() {
        return principle;
    }

    public void setprinciple(double principle) {
        this.principle = principle;
    }

    public double getCustomerId() {
        return customerId;
    }

    public void setCustomerId(double customerId) {
        this.customerId = customerId;
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

    /*@Override
    public String toString() {
        return "Account : " +"\n"+
                "Customer ID = " + customerId +"\n"+
                "Account Type = '" + ACCT_TYPE + '\'' +"\n"+
                "Principle = " + principle + "\n";
    }*/

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", ACCT_TYPE='" + ACCT_TYPE + '\'' +
                ", principle=" + principle +
                '}';
    }
}
