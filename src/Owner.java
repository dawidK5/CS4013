import java.util.ArrayList;

public class Owner {
    private int ownerId;
    private String firstname;
    private String surname;
    private ArrayList<Property> currentlyOwnedProperties;

    public int getOwnerId() {
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
    public void registerProperty() {

    }
    public void viewListOfProperties() {
        // Property owners should be able to view a list of their properties
        // and the tax that is due currently per property and also any overdue tax
        // (hasn’t been paid for a previous year)
    }
    public void viewCurrentTax(Property p) {
        // could it be implemented in Property as static
    }
    public void viewOverdueTax() {
        // see viewListOfProperties
    }
}
