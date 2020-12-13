package main;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The TaxManager provides management functionality such as viewing tax statistics for Department of Environment
 */
public class TaxManager {
    private static TaxCalculator taxCal;

    public static void setData(String str, double value, int index){
        if(str.toUpperCase().equals("RATE")) setRate(value, index);
        else if(str.toUpperCase().equals("LEVY")) setLevys(value, index);
        else if(str.toUpperCase().equals("PROPERTY")) setPropertyValue(value, index);
        else if(str.toUpperCase().equals("FEES")) setFees(value, index);

    }
    /*
    private static String[] getValues(){
        return strAr;
    }

     */
    private static void setRate(double rate, int index){
        taxCal.setRate(rate, index);
    }
    private static void setLevys(double levy, int index){
        taxCal.setLevys(levy, index);
    }
    private static void setPropertyValue(double propertyValue, int index){
        taxCal.setPropertyValue(propertyValue, index);
    }
    private static void setFees(double fee, int index){
        taxCal.setFees(fee, index);
    }
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

    public static void viewOwnerTaxPaymentData(String ownerId) {
        new Owner(ownerId).viewPaymentsMadeForAllProperties();
    }
    public static void viewOverdueTaxForYear(int year) {
        CSVHandler csv = new CSVHandler();
        System.out.println("Tax overdue for " + year);
        System.out.println("Property\tOwner\tTax overdue");
        ArrayList<String> payData1 = csv.readFromTax(year);
        String eircode;
        // owner;
        // double taxDue;
        for (String s : payData1) {
            eircode = s.substring(0, s.indexOf(','));
            String[] values = s.split(",");
            String sYear = Integer.toString(year);
            for (int i = 0; i < values.length; i++) {
                if (values[i].equals(sYear)) {
                    System.out.printf("%s\t\t%s\t\t%.2f\n", eircode, values[i - 1], TaxCalculator.overdueFees(Double.parseDouble(values[i + 2]), year, LocalDate.now().getYear()));
                }
            }

        }
    }
        public static void viewOverdueTaxForYearArea(int year, String routingKey) {
            CSVHandler csv = new CSVHandler();
            System.out.println("Tax overdue for " +year);
            System.out.println("Property\tOwner\tTax overdue");
            ArrayList<String> payData1 = csv.readFromTax(year, routingKey);
            String eircode;
            // owner;
            // double taxDue;
            for(String s : payData1) {
                eircode = s.substring(0, s.indexOf(','));
                String[] values = s.split(",");
                String sYear = Integer.toString(year);
                for(int i=0; i<values.length; i++) {
                    if(values[i].equals(sYear)) {
                        System.out.printf("%s\t\t%s\t\t%.2f\n",eircode, values[i-1], TaxCalculator.overdueFees(Double.parseDouble(values[i+2]), year, LocalDate.now().getYear()));
                    }
                }

            }
        }
        public static void viewTotalTaxPaidPerArea(String routingKey) {
            CSVHandler csv = new CSVHandler();
            System.out.printf("Total property tax paid for area %s:\t%.2f\n",routingKey,csv.readTotalFromTax(routingKey));
        }

        public static void viewAverageTaxPaidPerArea(String routingKey) {
            CSVHandler csv = new CSVHandler();
            double average = csv.readAverageFromTax(routingKey);
            if(average !=0.0) {
                System.out.printf("Average property tax paid for area %s:\t%.2f\n",routingKey,average);
            } else {
                System.out.println("No tax payments found for area "+routingKey+".");
            }
        }

        public static void viewNumberAndPercentageOfTaxPaidPerArea(String routingKey) {
            CSVHandler csv = new CSVHandler();
            String[] numAndProc = csv.readNumAndProcFromTax(routingKey).split(",");
            System.out.printf("Number of properties in area %s where tax was paid:\t\t%d\n", routingKey, Integer.parseInt(numAndProc[0]));
            if(numAndProc[1].charAt(0) == '-') {
                System.out.printf("No tax payments found in area %s\n", routingKey);
            }
            System.out.printf("Percentage of properties where tax was paid in area %s:\t%.2f\n", routingKey, Double.parseDouble(numAndProc[1]));

        }

        public void findRevenueOnChange(TaxCalculator tc) {
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
            System.out.println("After changing tax details, the change in revenue collected would be "+(sum2-sum1)/sum1*100+"percent.");

        }

}
