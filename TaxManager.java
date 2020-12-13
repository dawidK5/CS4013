public class TaxManager {
    private static TaxCalculator taxCal;
    public static void viewPaymentDataForProperty(String eircode) {
        CSVHandler csv = new CSVHandler();
        String[] paymentData = csv.readFromTax(eircode).replaceFirst(eircode, "").split(",");
        System.out.println("Tax data for property "+eircode);
        System.out.println("Year\tmain.Owner\tAmount due\tPaid?");
        for (int i=paymentData.length-1; i-3>=0; i-=4) {
            System.out.printf("%s\t%s\t\t%s\t\t%s\n", paymentData[i-2],paymentData[i-3],paymentData[i-1],paymentData[i]);
        }
    }
    public static void setData(String str, double value, int index){
        if(str.toUpperCase().equals("RATE")) setRate(value, index);
        else if(str.toUpperCase().equals("LEVY")) setLevys(value, index);
        else if(str.toUpperCase().equals("PROPERTY")) setPropertyValue(value, index);
        else if(str.toUpperCase().equals("FEES")) setFees(value, index);

    }


    private static String[] getValues(){
        return strAr
    }
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

}
