package main;

import java.time.LocalDate;

/**
 * This class stores information on payments for tax due
 */
public class Payment implements Comparable {
    private String eircode;
    private String ownerId;
    private int yearDue;
    private int yearPaid;
    private double amount;

    /**
     * Cnstructor specifying payment details
     * @param eircode   the String with the eircode
     * @param ownerId   the String for ownerID
     * @param yearDue   the integer year that tax payment was due
     * @param yearPaid  the integer when the tax was paid
     * @param amount    the double of amount to be paid
     */
    public Payment(String eircode, String ownerId, int yearDue, int yearPaid, double amount) {
        this.eircode = eircode;
        this.ownerId = ownerId;
        this.yearDue = yearDue;
        this.yearPaid = yearPaid;
        this.amount = amount;
    }

    /**
     * Makes a payment and updates the CSV files for payments
     */
    public void makePayment() {
        // update payment status to 'paid' with the new amount
        CSVHandler csv = new CSVHandler();
        if(yearDue < LocalDate.now().getYear()) {
            csv.changeTaxPaymentStatus(eircode, yearDue, TaxCalculator.overdueFees(amount,yearDue,LocalDate.now().getYear()));
        } else {
            csv.changeTaxPaymentStatus(eircode, yearDue, amount);
        }
    }

    /**
     * Returns the ircode of wehich this payment was made
     * @return  the String for eircode
     */
    public String getEircode() {
        return eircode;
    }

    /**
     * Gets the year for which the tax for this payment was due
     * @return  the integer for the year
     */
    public int getYearDue() {
        return yearDue;
    }

    /**
     * Obtains the year when this payment was made
     * @return  the integer for year paid
     */
    public int getYearPaid() {
        return yearPaid;
    }

    /**
     * Gets the amount paid in this payment
     * @return  the double for the amount paid this
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sorts payments by year with descending order
     * @param o     the object for comparison
     * @return      negative number ifn smaller or bigger t
     */
    @Override
    public int compareTo(Object o) {
        return ((Payment)o).getYearDue() - this.getYearDue();
    }
    // year descending

    /*
    private double getAmount(String str){
        String[] values = str.split(",");
        String marketValue = "";
        String locationType = "";
        String primaryResidence = "";
        for (int i = 0; i < values.length; i++) {
            marketValue = ifGet(i, 1, values);
            locationType = ifGet(i, 2, values);
            primaryResidence = ifGet(i, 3, values);
        }
        TaxCalculator tax = new TaxCalculator(Integer.parseInt(marketValue), locationType, primaryResidence);
        return tax.getTax();
    }

    private String ifGet(int i, int index, String[] strAr) {
        String str = "";
        if(i == index){
            str = strAr[index];
        }
        return str;
    }

     */

}