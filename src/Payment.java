import java.time.LocalDate;

/**Payment class
 *
 * */
public class Payment {
    private String eircode;
    private String ownerId;
    private int year;
    private double amount;

    public Payment(String eircode, String ownerId, int year, double amount) {
        this.eircode = eircode;
        this.ownerId = ownerId;
        this.year = year;
        this.amount = amount;
    }

    public void makePayment() {
        // update payment status to 'paid' with the new amount
        CSVHandler csv = new CSVHandler();
        if(year > LocalDate.now().getYear()) {
            csv.changeTaxPaymentStatus(eircode, year, TaxCalculator.overdueFees(amount,year,LocalDate.now().getYear()));
        } else {
            csv.changeTaxPaymentStatus(eircode, year, amount);
        }
    }

    public String getEircode() {
        return eircode;
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


    public String format() {
        return String.format("%s,%d,%.2f",ownerId,year,amount);
    }
}