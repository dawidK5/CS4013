import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Owner {
    private String ownerId; //PPSN
    private String firstname;
    private String surname;
    private ArrayList<Property> currentlyOwnedProperties;
    private ArrayList<Payment> paymentsMade; // ever, even for sold properties

    //cache data for the owner
    public Owner(String ownerId) {
        CSVHandler csv = new CSVHandler();
        String ownerInfo[] = csv.readFromOwners(ownerId).replace(ownerId+",", "").split(",");
        this.ownerId = ownerId;
        this.firstname = ownerInfo[0];
        this.surname = ownerInfo[1];
        // parse all ever-owned eircodes
        for (int i=2; i<ownerInfo.length; i++) {
            // decide which properties are currently owned
            // get property details by eircode from csv
            String pd[] = csv.readFromProperties(ownerInfo[i]).split(",");
            // pd means property details
            // if a property's current owner is this owner then
            if(pd[pd.length-1] == this.ownerId) {
                currentlyOwnedProperties.add(new Property(pd[0], pd[1], Integer.parseInt(pd[2]), pd[3], pd[4], this.ownerId));
            }
            // use same eircode to check if this owner made payments for this property
            String payments[] = csv.readFromTax(pd[0]).replace(pd[0]+",", "").split(",");
            for(int j=0; j<payments.length-4; j+=4) {
                // if this owner paid tax for this property, add a new payment to arraylist of payments
                if(payments[j].equals(this.ownerId) && payments[j+3].equals("paid")) {
                    paymentsMade.add(new Payment(pd[0], this.ownerId, payments[j+2]));
                }

            }
        }

    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getFirstname() {
        return firstname;
    }
    public String getSurname() {
        return surname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void getBalancingStatementFor(int year) {
        // query specific previous years and get a balancing statement for
        // any particular year or property

    }
    public boolean registerProperty(String eircode, String address, int estMarketValue, String locationCategory, String principalPrivateRes) {
        address.replaceAll(",","");
        Property p = new Property(eircode, address, estMarketValue, locationCategory, principalPrivateRes, ownerId);
        currentlyOwnedProperties.add(p);

        try {
            CSVHandler csv = new CSVHandler();
            String line = csv.readFromProperties(p.getEircode());
            if(line != null) {
                csv.removeLine("property", line);
            }
            csv.writeToProperties(p.toString());
            return true;
        } catch(IOException ex) {
            return false;
        }

    }
    public void viewListOfProperties() {
        System.out.println("List of your currently owned properties:");
        for(int i=0; i<currentlyOwnedProperties.size(); i++) {
            System.out.println("Property"+i);
            System.out.println(currentlyOwnedProperties.get(i).getAddress());
            System.out.println();
        }
        // Property owners should be able to view a list of their properties
        // and the tax that is due currently per property and also any overdue tax
        // (hasn’t been paid for a previous year)
    }
    public void viewCurrentTax(Property p) {
        System.out.println("Balancing statement for property "+p.getEircode()+":");
        System.out.printf("This year's current tax due:\t %.2f\n", p.getCurrentTax());
        System.out.println();
    }
    public void viewOverdueTax() {
        CSVHandler csv = new CSVHandler();
        // find all properties ever owned by an owner to see if they didn't forget to pay
        String allProperties[] = csv.readFromOwners(ownerId).replace(ownerId+",", "").split(",");
        System.out.printf("Tax overdue (from previous years): "); // only tax to be paid by THIS owner
        for(String eircode : allProperties) {
            String history[] = csv.readFromTax(eircode).replace(eircode+",", "").split(",");
            for(int i =0; i < history.length; i=i+4) {
                if (history[i].equals(ownerId) && history[i+3].equals("notpaid")) {
                    double due =  TaxCalculator.overdueFees(Double.parseDouble(history[i+2]), Integer.parseInt(history[i+1]), LocalDate.now().getYear());
                    System.out.printf("%s: \t %.2f", history[i+1], due);
                }
            }
            System.out.println();
        }

    }
}
