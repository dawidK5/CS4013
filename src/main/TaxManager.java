package main;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The TaxManager provides management functionality such as viewing tax statistics for Department of Environment
 * and has its own modifiable instance of TaxCalculator
 */
public class TaxManager {
    private static TaxCalculator taxCal;

    /**
     * No-arg constructor for TaxManager
     */
    public TaxManager () {
        taxCal = new TaxCalculator();
    }

    /**
     * Sets the rate, levy, property value boundary, or fees for new instance of TaxCalculator and returns it
     * @param str       the String for selecting property to change
     * @param value     the double of new value of rates/fees
     * @param index     the integer for index of property in TaxCalculator
     * @return          modfied instance of TaxCalculator
     */
    public void setData(String str, double value, int index){
        if(str.toUpperCase().equals("RATE")) setRate(value, index);
        else if(str.toUpperCase().equals("LEVY")) setLevys(value, index);
        else if(str.toUpperCase().equals("PROPERTY")) setPropertyValue(value, index);
        else if(str.toUpperCase().equals("FEES")) setFees(value, index);
    }

    /**
     * Returns the modified TaxCalculator
     * @return  the TaxCalculator of this TaxManager
     */
    public TaxCalculator getTaxCal() {
        return this.taxCal;
    }
    /*
    private static String[] getValues(){
        return strAr;
    }

     */

    /**
     * Helper method that sets the rate of tax for TaxCalculator
     * @param rate      the double for new value of tax rate
     * @param index     the index of value to be changed
     */
    private void setRate(double rate, int index){
        taxCal.setRate(rate, index);
    }

    /**
     * Helper method that sets the tax levy to the value given
     * @param levy      the double for new value of levy
     * @param index     the int for index of tax value to be changed
     */
    private void setLevys(double levy, int index){
        taxCal.setLevys(levy, index);
    }

    /**
     * Helper method that sets the specified property value tax boundary to value given
     * @param propertyValue     the double for the new property value
     * @param index             the integer for index of tax value to be changed
     */
    private void setPropertyValue(double propertyValue, int index){
        taxCal.setPropertyValue(propertyValue, index);
    }

    /**
     * Helper method that sets the tax fees
     * @param fee       the new value of tax fees
     * @param index     the integer for index of tax fees to be changed
     */
    private void setFees(double fee, int index){
        taxCal.setFees(fee, index);
    }

    /**
     * Allows to view the tax payment data in the system for a particular property
     * @param eircode   the String for eircode to be queried
     */
    public static void viewPaymentDataForProperty(String eircode) {
        CSVHandler csv = new CSVHandler();
        String[] payData1 = csv.readFromTax(eircode).replaceFirst(eircode, "").split(",");
        System.out.println("Tax data for property "+eircode);
        System.out.println("Year\tOwner\tTax due\t\tYear paid\tAmount Paid");
        for (int i=payData1.length-1; i-4>=0; i-=5) {
            System.out.printf("%s\t%s\t\t%.2f\t\t%s\t\t%.2f\n", payData1[i-3],payData1[i-4],(Double.parseDouble(payData1[i-1])),payData1[i-2],
                    (payData1[i-1].charAt(0) == '0') ? 0 : TaxCalculator.overdueFees(Double.parseDouble(payData1[i-1]),Integer.parseInt(payData1[i-3]),Integer.parseInt(payData1[i-2])));
        }
    }

    /**
     * Displays the tax payment data for a particular owner
     * @param ownerId   the String for owner ID (PPSN)
     */
    public static void viewOwnerTaxPaymentData(String ownerId) {
        new Owner(ownerId).viewPaymentsMadeForAllProperties();
    }

    /**
     * Shows all tax overdue for a particular year in the system
     * @param year      the integer for year to be queried
     */
    public static void viewOverdueTaxForYear(int year) {
        CSVHandler csv = new CSVHandler();
        System.out.println("Tax overdue for " + year);
        ArrayList<String> payData1 = csv.readFromTax(year);
        ArrayList<String> overdue = new ArrayList<>();
        String eircode;
        boolean found = false;
        for (String s : payData1) {
            eircode = s.substring(0, s.indexOf(','));
            String[] values = s.split(",");
            String sYear = Integer.toString(year);
            for (int i = 2; i < values.length-2; i+=5) {
                if (values[i].equals(sYear)) {
                    overdue.add(String.format("%s\t\t%s\t\t%.2f\n", eircode, values[i - 1], TaxCalculator.overdueFees(Double.parseDouble(values[i + 2]), year, LocalDate.now().getYear())));
                    found = true;
                }
            }
        }
        if(found) {
            System.out.println("Property\tOwner\tTax overdue");
            overdue.forEach(s -> System.out.print(s));
        } else {
            System.out.println("No tax overdue found");
        }
    }

    /**
     * Allows to view overdue tax per particular area based on the routing key of eircode
     * @param year          the integer for year to be queried
     * @param routingKey    the String for the routing key of the eircode to be queried
     */
    public static void viewOverdueTaxForYearArea(int year, String routingKey) {
        CSVHandler csv = new CSVHandler();
        System.out.println("Tax overdue for " +year +", for area "+routingKey);
        ArrayList<String> payData1 = csv.readFromTax(year, routingKey);
        ArrayList<String> overdue = new ArrayList<>();
        String eircode;
        boolean found = false;
        for(String s : payData1) {
            eircode = s.substring(0, s.indexOf(','));
            String[] values = s.split(",");
            String sYear = Integer.toString(year);
            for(int i=2; i<values.length-2; i+=5) {
                if(values[i].equals(sYear)) {
                    overdue.add(String.format("%s\t\t%s\t\t%.2f\n",eircode, values[i-1], TaxCalculator.overdueFees(Double.parseDouble(values[i+2]), year, LocalDate.now().getYear())));
                    found = true;
                }
            }
        }
        if(found) {
            System.out.println("Property\tOwner\tTax overdue");
            overdue.forEach(s -> System.out.print(s));
        } else {
            System.out.println("No tax overdue found");
        }
    }

    /**
     * Displays the total tax paid per routing key area
     * @param routingKey    the String for the routing key of area to be queried
     */
    public static void viewTotalTaxPaidPerArea(String routingKey) {
        CSVHandler csv = new CSVHandler();
        System.out.printf("Total property tax paid for area %s:\t%.2f\n",routingKey,csv.readTotalFromTax(routingKey));
    }

    /**
     * Allows to view average tax paid per routing key area
     * @param routingKey    the String with the routing key
     */
    public static void viewAverageTaxPaidPerArea(String routingKey) {
        CSVHandler csv = new CSVHandler();
        double average = csv.readAverageFromTax(routingKey);
        if(average !=0.0) {
            System.out.printf("Average property tax paid for area %s:\t%.2f\n",routingKey,average);
        } else {
            System.out.println("No tax payments found for area "+routingKey+".");
        }
    }

    /**
     * Shows the number of properties and percentage of tax paid per area
     * @param routingKey    the String for the routing key to be queried
     */
    public static void viewNumberAndPercentageOfTaxPaidPerArea(String routingKey) {
        CSVHandler csv = new CSVHandler();
        String[] numAndProc = csv.readNumAndProcFromTax(routingKey).split(",");
        System.out.printf("Number of properties in area %s where tax was paid:\t\t%d\n", routingKey, Integer.parseInt(numAndProc[0]));
        if(numAndProc[1].charAt(0) == '-') {
            System.out.printf("No tax payments found in area %s\n", routingKey);
        }
        System.out.printf("Percentage of properties where tax was paid in area %s:\t%.2f\n", routingKey, Double.parseDouble(numAndProc[1]));

    }

    /**
     * Allows to inspect the change in tax to be collected for a new tax calculator
     * @param tc    the TaxCalculator with modified parameters for tax calculation rules
     */
    public void findPredictedRevenueOnChange(TaxCalculator tc) {
        CSVHandler csv = new CSVHandler();
        ArrayList<String> taxInfo = csv.readFromTax(LocalDate.now().getYear());
        double sum1 = 0;
        double sum2 = 0;
        TaxCalculator tc2 = new TaxCalculator();
        for(String s : taxInfo) {
            tc.reload(s);
            sum1 += tc.getTax();
            tc2.reload(s);
            sum2+= tc2.getTax();
        }
        System.out.println("After changing tax details, the change in revenue collected would be "+(sum2-sum1)/sum1*100+" percent.");

    }

}
