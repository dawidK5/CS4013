/**Payment class
 *
 * */
public class Payment {
    private String eircode;
    private String ownerId;
    private int year;
    private double amount;
    CSVHandler csv = new CSVHandler();

    public Payment(String eircode, String ownerId, int year, double amount) {
        this.eircode = eircode;
        this.ownerId = ownerId;
        this.year = year;
        this.amount = amount;
    }

    public void makePayment(String eircode) {
        String str = csv.readFromProperties(eircode);
        double tax = getAmount(str);
        // csv.writeToTax(tax);
    }

    public int getYear() {
        return year;
    }

    public double getAmount() {
        return amount;
    }

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

    @Override
    public String toString() {
        return String.format("%s,%d,%.2f");
    }

    public String getEircode() {
        return eircode;
    }
}