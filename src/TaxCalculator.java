import java.util.*;
import java.util.Scanner;

public class TaxCalculator{
    private double fees;
    private String location;
    private double propertyValue;
    private double propertyTax;
    private int year; // Chosen year
    // private int resident;
    private int cYear; // current year
    // private int residentOwner;
    private boolean principalRes;

    public TaxCalculator(int estMarketValue, String locationCategory, String principalPrivateRes) {
        propertyValue = estMarketValue;
        location = locationCategory;
        principalRes = principalPrivateRes.equals("YES") ? true : false;
    }


    public void livingAreaTax(){
         
        if (location == "City"){
            fees = fees + 100;
        } else if(location == "Large Town"){
            fees = fees + 80;
        }else if(location == "Small Town"){
            fees = fees + 60;
        }else if(location == "Village"){
            fees = fees + 50;
        }else if(location == "CountrySide"){
            fees = fees + 25;
        }
        
     }
     public void propertyTax(){
         if (propertyValue > 650000){
             propertyTax = propertyValue * 0.0004;
             fees = fees + propertyTax;
            } else if(propertyValue <= 650000 && propertyValue >= 400000){
             propertyTax = propertyValue * 0.0002;
             fees = fees + propertyTax;
            }else if(propertyValue <= 400000 && propertyValue >= 150000){
             propertyTax = propertyValue * 0.0001;
             fees = fees + propertyTax;
            } else{
             fees = fees;
           }
     }
    public void overdueFees(){
        int x;
        int label= year - cYear;
        // We will get a negative number
        // We can turn a negative number back into a positive number by multiplying it by -1
        int S = label * -1;
        String overdue= String.valueOf(S);
        for(x =0; x<overdue.length(); x++){
            fees = fees * 0.07;
        }
    }
    /*
    public boolean residentOwner(){
        //Finds if resident owner lives in house

        if(residentOwner == resident) {
         
          return true;
      }else {
          fees = fees + 100;
          return false;
        }
        }
         */
    public void principalResFees() {
        if (!principalRes) {
            fees += 100;
        }
    }

    public void fees(){
        fees = fees + 100;
        
    }
    public double getTax() {
        fees();
        propertyTax();
        livingAreaTax();
        principalResFees();
        return fees;
    }
}
