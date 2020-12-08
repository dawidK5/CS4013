
public class Payment {
    private double OutStandingFees;
    private double Fees;
    private double totalPaid;
    private double LivingAreaTax;
    private double PropertyTax;
    private String Location;
    public Payment(double OutStandingFees,double PropertyTax, double Fees, double totalPaid,double LivingAreaTax){
        this.OutStandingFees = OutStandingFees;
        this.Fees = Fees;
        this.totalPaid = totalPaid;
        this.LivingAreaTax = LivingAreaTax;
        this.PropertyTax = PropertyTax;
    }
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
         
        }
        
     
    public void Fees(){
        
    }
    public void totalPaid(){
        
    }
    
}
