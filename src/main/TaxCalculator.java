package main;

/**
 * The TaxCalculator class is used to calculate tax for a given property
 */
public class TaxCalculator{
    private double fees;
    private String location;
    private double propertyValue;
    private double propertyTax;
    // 0=city 1=largeTown 2=smallTown 3=village 4=countryside
    private double[] rates = {100, 80, 60, 50, 25};
    // 0=maxValue 1=middleValue 2=minValue
    private double[] propertyValues = {650000, 400000, 150000};
    // 0=propertyValue>maxValue 1=propertyValue>=middleValue 2=propertyValue>=minValue
    private double[] levys = {0.0004, 0.0002, 0.0001};
    // 0=setFee 1=principalResidenceFee
    private double[] feesArr = {100, 100};
    private boolean principalRes;

    /**
     * No-arg constructor for TaxCalculator
     */
    public TaxCalculator() {

    }

    /**
     * Sets rate of tax for location category at a valid index
     * @param rate      the double for the new rate of tax
     * @param index     the integer for index at which the tax is changed: 0=city 1=largeTown 2=smallTown
     *                  3=village 4=countryside
     */
    public void setRate(double rate, int index){
        index = checker(index, rates);
        rates[index] = rate;
    }

    /**
     * Sets the value of levy for property value percentage tax rate for a valid property value range
     * @param levy      the double ew levy value to be applied to this TaxCalculator
     * @param index     the integer for index in the array: 0=propertyValue>maxValue 1=propertyValue>=middleValue
     *                  2=propertyValue>=minValue
     */
    public void setLevys(double levy, int index){
        index = checker(index, levys);
        levys[index] = levy;
    }

    /**
     * Sets the value of property value tax range for this TaxCalculator
     * @param propertyValue     the double for the new property value tax interval
     * @param index             the integer of index for the interval boundary to be modified: 0=maxValue
     *                          1=middleValue 2=minValue
     */
    public void setPropertyValue(double propertyValue, int index){
        index = checker(index, propertyValues);
        propertyValues[index] = propertyValue;
    }

    /**
     * Sets the fee for the fixed charge or the charge if this property isn't principal private residence
     * @param fee       the double for the new fee
     * @param index     the integer for index of array with value to be modified: 0=setFee 1=principalResidenceFee
     */
    public void setFees(double fee, int index){
        index = checker(index, feesArr);
        feesArr[index] = fee;
    }

    /**
     * Constructor specifying property details for tax calculation
     * @param estMarketValue        the int for property market value
     * @param locationCategory      the String of locaction type
     * @param principalPrivateRes   the String for YES or NO
     */
    public TaxCalculator(int estMarketValue, String locationCategory, String principalPrivateRes) {
        propertyValue = estMarketValue;
        location = locationCategory;
        principalRes = principalPrivateRes.equals("YES");
    }

    /**
     * Calculates fees overdue from previous years
     * @param fees      the double for fees up to this moment
     * @param year      the integer for year when tax was originally due
     * @param cYear     the String for the year when the tax was paid
     * @return          the total fees overdue using this TaxCalculator's rates
     */
    public static double overdueFees(double fees, int year, int cYear){
        int x;
        int label= year - cYear;
        // We will get a negative number
        // We can turn a negative number back into a positive number by multiplying it by -1
        int s = label * -1;
        for(x =0; x<s; x++){
            fees = fees + fees * 0.07;
        }
        return fees;
    }



    /**
     * Calculates the tax based on location category of the property to be taxed
     */
    public void livingAreaTax(){
        // 0=city 1=largeTown 2=smallTown 3=village 4=countryside
        if (location.equals("City")){
            fees = fees + rates[0];
        } else if(location.equals("Large Town")){
            fees = fees + rates[1];
        }else if(location.equals("Small Town")){
            fees = fees + rates[2];
        }else if(location.equals("Village")){
            fees = fees + rates[3];
        }else if(location.equals("Countryside")){
            fees = fees + rates[4];
        }

    }

    /**
     * Calculates tax based on property value
     */
    public void propertyTax(){
        if (propertyValue > propertyValues[0]){
            propertyTax = propertyValue * levys[0];
            fees = fees + propertyTax;
        } else if(propertyValue <= propertyValues[0] && propertyValue >= propertyValues[1]){
            propertyTax = propertyValue * levys[1];
            fees = fees + propertyTax;
        }else if(propertyValue <= propertyValues[1] && propertyValue >= propertyValues[1]){
            propertyTax = propertyValue * levys[2];
            fees = fees + propertyTax;
        }
    }

    /**
     * Calculates tax based on whether a property was marked as principal private residence
     */
    public void principalResFees() {
        if (!principalRes) {
            fees += feesArr[1];
        }
    }

    /**
     * Add a fixed charge fee to the tax to be calculated
     */
    public void fees(){
        fees = fees + feesArr[0];
    }

    /**
     * Calculates annual tax based on current property information in this tax calculator
     * @return      the double for total fees to be paid
     */
    public double getTax() {
        fees();
        propertyTax();
        livingAreaTax();
        principalResFees();
        return fees;
    }

    /**
     * Checks for the correctness of input values for the specified index in list of tax rates
     * @param index     the integer for index to be checked
     * @param list      the double array of rates one of which is to be modified
     * @return          correct index to be accessed for modification
     */
    private int checker(int index, double[] list) {
        if (index > list.length - 1) index = list.length - 1;
        else if (index < 0) index = 0;
        return index;
    }

    /**
     * Load data of another property into this TaxCalculator
     * @param line      the String in csv format that contains
     */
    public void reload(String line) {
        String[] values = line.split(",");
        this.propertyValue = Double.parseDouble(values[2]);
        this.location = values[3];
        this.principalRes = values[4].equals("YES");
        this.fees = 0;
    }
}