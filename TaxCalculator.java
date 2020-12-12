
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
    private double[] levies = {0.0004, 0.0002, 0.0001};
    // 0=setFee 1=principalResidenceFee
    private double[] feesArr = {100, 100};
    private boolean principalRes;

    private void setRate(double rate, int index){
        if(index > rates.length-1)index= rates.length-1;
        else if(index < 0)index = 0;
        rates[index] = rate;
    }
    private void setLevies(double levie, int index){
        levies[index] = levie;
    }
    private void setPropertyValue(double propertyValue, int index){
        propertyValues[index] = propertyValue;
    }

    public TaxCalculator(int estMarketValue, String locationCategory, String principalPrivateRes) {
        propertyValue = estMarketValue;
        location = locationCategory;
        principalRes = principalPrivateRes.equals("YES");
    }
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
    // 0=city 1=largeTown 2=smallTown 3=village 4=countryside
    public void livingAreaTax(){

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

    public void propertyTax(){
        if (propertyValue > propertyValues[0]){
            propertyTax = propertyValue * levies[0];
            fees = fees + propertyTax;
        } else if(propertyValue <= propertyValues[0] && propertyValue >= propertyValues[1]){
            propertyTax = propertyValue * levies[1];
            fees = fees + propertyTax;
        }else if(propertyValue <= propertyValues[1] && propertyValue >= propertyValues[1]){
            propertyTax = propertyValue * levies[2];
            fees = fees + propertyTax;
        }
    }

    public void principalResFees() {
        if (!principalRes) {
            fees += feesArr[1];
        }
    }

    public void fees(){
        fees = fees + feesArr[0];

    }

    public double getTax() {
        fees();
        propertyTax();
        livingAreaTax();
        principalResFees();
        return fees;
    }

}
