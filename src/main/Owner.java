package main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The owner is a class that stores information about a property owner
 */
public class Owner {
    private String ownerId; //PPSN
    private String firstname;
    private String surname;
    private ArrayList<Property> currentlyOwnedProperties;
    private ArrayList<Payment> taxOverdue;
    private ArrayList<Payment> paymentsMade; // ever, even for sold properties

    //cache data for the owner

    /**
     * Constructor specifying the OwnerId
     * @param ownerId   the String of ownerId as in owners.csv file
     */
    public Owner(String ownerId) {
        CSVHandler csv = new CSVHandler();
        String[] ownerInfo = csv.readFromOwners(ownerId).replace(ownerId + ",", "").split(",");
        this.ownerId = ownerId;
        this.firstname = ownerInfo[0];
        this.surname = ownerInfo[1];
        // parse all ever-owned eircodes
        currentlyOwnedProperties = new ArrayList<>();
        paymentsMade = new ArrayList<>();
        taxOverdue = new ArrayList<>();

        for (int i = 2; i < ownerInfo.length; i++) {
            // decide which properties are currently owned
            // get property details by eircode from csv
            String[] pd = csv.readFromProperties(ownerInfo[i]).split(",");
            // pd means property details
            // if a property's current owner is this owner then
            if (pd[pd.length - 1].equals(this.ownerId)) {
                currentlyOwnedProperties.add(new Property(pd[0], pd[1], Integer.parseInt(pd[2]), pd[3], pd[4], this.ownerId));
            }
            // use same eircode to check if this owner made payments for this property
            String[] payments = csv.readFromTax(pd[0]).replace(pd[0] + ",", "").split(",");
            for (int j = 0; j <= payments.length - 5; j += 5) {
                // if this owner paid tax for this property, add a new payment to arraylist of payments
                if (payments[j].equals(this.ownerId)) {
                    if(payments[j + 4].equals("paid")) {
                        paymentsMade.add(new Payment(pd[0], this.ownerId, Integer.parseInt(payments[j + 1]), Integer.parseInt(payments[j + 2]), Double.parseDouble(payments[j + 3])));
                    } else {
                        taxOverdue.add(new Payment(pd[0], this.ownerId, Integer.parseInt(payments[j + 1]), 0, Double.parseDouble(payments[j + 3])));
                    }
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

    /*
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
     */

    /**
     * Forms a balancing statement with tax due and payed for this propert owner from a given year
     * @param year  int of ear for balancing statement
     */
    public void getBalancingStatementFor(int year) {
        System.out.println("Balancing statement for year " + year);
        ArrayList<Payment> entries = new ArrayList<Payment>();
        paymentsMade.forEach(p -> {if(p.getYearPaid() == year) {entries.add(p);}} );
        taxOverdue.forEach(p -> {if(p.getYearDue() == year) {entries.add(p);}} );
        if(entries.isEmpty()) {
            System.out.println("No tax due or paid for that period");
            return;
        }
        System.out.println("Property\tPaid\tDue\t\tBalance");
        double sum = 0;
        for (Payment tax : entries) {
            if (taxOverdue.contains(tax)) {
                sum = sum + tax.getAmount();
                System.out.printf("%s\t\t\t\t%.2f\t%.2f\n", tax.getEircode(), tax.getAmount(), sum);
            } else {
                System.out.printf("%s\t\t\t\t%.2f\t%.2f\n", tax.getEircode(), tax.getAmount(), sum + tax.getAmount());
                System.out.printf("%s\t\t%.2f\t\t\t%.2f\n", tax.getEircode(), tax.getAmount(), sum);
            }
        }
    }

    public ArrayList<Payment> getPaymentsMade() {
        return paymentsMade;
    }

    public void viewPaymentsMadeForAllProperties() {
        System.out.println("Payments made for all properties:");
        Collections.sort(paymentsMade);
        System.out.println("Property\tAmount\tYear");
        for(Payment p : paymentsMade) {
            if(p.getYearPaid() != 0)
                System.out.printf("%s\t\t%.2f\t%d\n", p.getEircode(),p.getAmount(), p.getYearPaid());
        }
        System.out.println();
    }


    public boolean registerProperty(String eircode, String address, int estMarketValue, String locationCategory, String principalPrivateRes) {
        address = address.replaceAll(",", "");
        Property p = new Property(eircode, address, estMarketValue, locationCategory, principalPrivateRes, ownerId);
        currentlyOwnedProperties.add(p);

        try {
            CSVHandler csv = new CSVHandler();
            String line = csv.readFromProperties(p.getEircode());
            if (line != null) {
                csv.removeLine("property", line);
            }
            line += String.format(",%d,0,%.2f,notpaid", LocalDate.now().getYear(), p.getCurrentTax());
            csv.writeToProperties(line);
            return true;
        } catch (IOException ex) {
            return false;
        }

    }

    public ArrayList<Property> getListOfProperties() {
        return currentlyOwnedProperties;
    }
public void viewListOfProperties() {
        System.out.println("List of your currently owned properties:");
        for (int i = 0; i < currentlyOwnedProperties.size(); i++) {
            System.out.println("Property " + (i+1));
            System.out.println(currentlyOwnedProperties.get(i).getAddress());
            System.out.println();
        }
    }

    private ArrayList<Property> getAllProperties() {
        ArrayList<Property> allProperties = new ArrayList<>();
        CSVHandler csv = new CSVHandler();
        String[] eircodes = csv.readFromOwners(ownerId).replace(ownerId + ",*,*,", "").split(",");
        for(String e : eircodes) {
            String[] params = csv.readFromProperties(e).split(",");
            allProperties.add(new Property(params[0], params[1], Integer.parseInt(params[2]), params[3], params[4], params[5]));
        }
        return allProperties;
    }


    public void viewCurrentTax(Property p) {
        System.out.printf("This year's current tax for %s:\t %.2f\n", p.getEircode(), p.getCurrentTax());
        System.out.println();
    }

    public ArrayList<Payment> getOverdueTax() {
        Collections.sort(taxOverdue);
        return taxOverdue;
    }
    public void viewOverdueTax() {
        Collections.sort(taxOverdue);
        for(Payment p : taxOverdue) {
            double due = TaxCalculator.overdueFees(p.getAmount(), p.getYearDue(), LocalDate.now().getYear());
            System.out.printf("%s: \t %.2f\n", p.getYearDue(), due);
        }
    }
    /*
    public void viewOverdueTax() {
        CSVHandler csv = new CSVHandler();
        // find all properties ever owned by an owner to see if they didn't forget to pay
        String[] allProperties = csv.readFromOwners(ownerId).replace(ownerId + ",", "").split(",");
        System.out.printf("Tax overdue (from previous years): "); // only tax to be paid by THIS owner
        for (String eircode : allProperties) {
            String[] history = csv.readFromTax(eircode).replace(eircode + ",", "").split(",");
            for (int i = 0; i < history.length; i = i + 4) {
                if (history[i].equals(ownerId) && history[i + 3].equals("notpaid")) {
                    double due = TaxCalculator.overdueFees(Double.parseDouble(history[i + 2]), Integer.parseInt(history[i + 1]), LocalDate.now().getYear());
                    System.out.printf("%s: \t %.2f", history[i + 1], due);
                }
            }
            System.out.println();
        }
    }

     */

    public void payTax(Payment p) {
        p.makePayment();
        paymentsMade.add(p);
        taxOverdue.remove(p);
    }
}
