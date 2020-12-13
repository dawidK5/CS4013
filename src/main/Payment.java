package main;

import java.time.LocalDate;

/**main.Payment class
 *
 * */
public class Payment implements Comparable{
    private String eircode;
    private String ownerId;
    private int yearDue;
    private int yearPaid;
    private double amount;

    public Payment(String eircode, String ownerId, int yearDue, int yearPaid, double amount) {
        this.eircode = eircode;
        this.ownerId = ownerId;
        this.yearDue = yearDue;
        this.yearPaid = yearPaid;
        this.amount = amount;
    }

    public void makePayment() {
        // update payment status to 'paid' with the new amount
        CSVHandler csv = new CSVHandler();
        // if
        if(yearDue < LocalDate.now().getYear()) {
            csv.changeTaxPaymentStatus(eircode, yearDue, TaxCalculator.overdueFees(amount,yearDue,LocalDate.now().getYear()));
        } else {
            csv.changeTaxPaymentStatus(eircode, yearDue, amount);
        }
    }

    public String getEircode() {
        return eircode;
    }

    public int getYearDue() {
        return yearDue;
    }

    public int getYearPaid() {
        return yearPaid;
    }

    public double getAmount() {
        return amount;
    }

    public String format() {
        return String.format("%s,%d,%d,%.2f",ownerId,yearDue,yearPaid,amount);
    }
    // year descending


    // @Override
    public int compareTo(Object o) {
        return ((Payment)o).getYearDue() - this.getYearDue();
    }


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
        main.TaxCalculator tax = new main.TaxCalculator(Integer.parseInt(marketValue), locationType, primaryResidence);
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