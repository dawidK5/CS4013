import java.io.IOException;
import java.util.ArrayList;

public class Owner {
    private String ownerId; //PPSN
    private String firstname;
    private String surname;
    private ArrayList<Property> currentlyOwnedProperties;

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
        Property p = new Property(eircode, address, estMarketValue, locationCategory, principalPrivateRes);
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
    public void viewOverdueTax(Property p) {
        CSVHandler csv = new CSVHandler();
        System.out.printf("Tax overdue (from previous years): "); // only tax to be paid by THIS owner
        String history[] = csv.readFromTax(p.getEircode()).replace(p.getEircode(), "").split(",");
        for(int i =0; i < history.length; i++) {
            if (history[i].equals(ownerId) && history[i+3].equals("notpaid")) {
                System.out.println(history[i+1]+": \t"+history[i+2]);
            }
        }
        System.out.println();
        // see viewListOfProperties
    }
}
