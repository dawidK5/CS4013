import java.util.*;
import java.util.Scanner;

public class TaxCalculator{
    private double Fees;
    private String Location;
    private double PropertyValue;
    private double PropertyTax;
    private int Year; // Chosen year
    private int Resident;
    private int Cyear; // current year
    private int ResidentOwner;
   public void LivingAreaTax(String Location){  
         
         if (Location == "City"){
            Fees = Fees + 100;
        } else if(Location == "Large Town"){
            Fees = Fees + 80;
        }else if(Location == "Small Town"){
            Fees = Fees + 60;
        }else if(Location == "Village"){
            Fees = Fees + 50;
        }else if(Location == "CountrySide"){
            Fees = Fees + 25;
        }
        
     }
     public void PropertyTax(){
         
         if (PropertyValue > 650000){
             PropertyTax = Fees * 0.0004;
             Fees = Fees + PropertyTax;
            } else if(PropertyValue <= 650000 && PropertyValue >= 400000){
             PropertyTax = Fees * 0.0002;
             Fees = Fees + PropertyTax;
            }else if(PropertyValue <= 400000 && PropertyValue >= 150000){
             PropertyTax = Fees * 0.0001;
             Fees = Fees + PropertyTax;
            } else{
             Fees = Fees;
        
           }
     }
    public void OverdueFees(){
        int x;
        int Label= Year - Cyear;
        // We will get a negative number
        // We can turn a negative number back into a positive number by multiplying it by -1
        int S = Label * -1;
        String OverDue= String.valueOf(S);
        for(x =0; x<OverDue.length(); x++){
            Fees = Fees * 0.07;
        }
    }
    public Boolean  ResidentOwner(){
        //Finds if resident owner lives in house
        if(ResidentOwner == Resident) {
         
          return true;
      }else {
          Fees = Fees + 100;
          return false;
        }
    }
    public void Fees(){
        Fees = Fees + 100;
        
    }
}
