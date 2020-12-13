package main;

/**
 * The Property class stores details about a property based on an information from the csv files.
 */
public class Property {
    private String eircode;
    private String address;
    private int estMarketValue;
    private String locationCategory;
    private String principalPrivateRes;
    private String curOwnerId;

    /**
     * Constructor specifying property information
     * @param eircode               the String with full Eircode/postcode
     * @param address               the String with full address without commas
     * @param estMarketValue        the int for estimated market value of property
     * @param locationCategory      the String with location type of the property
     * @param principalPrivateRes   the String "YES" or "NO" depending whether this priperty is principal residence of current owner
     * @param curOwnerId            the String for unique ID, PPSN of current property owner
     */
    public Property(String eircode, String address, int estMarketValue, String locationCategory, String principalPrivateRes, String curOwnerId) {
        this.eircode = eircode;
        this.address = address;
        this.estMarketValue = estMarketValue;
        this.locationCategory = locationCategory;
        this.principalPrivateRes = principalPrivateRes;
        this.curOwnerId = curOwnerId;
    }

    /**
     * Obtain full eircode for this property
     * @return  String eircode
     */
    public String getEircode() {
        return eircode;
    }

    /**
     * Get address of this property
     * @return the String with full address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns estimated market value of this property
     * @return the integer value of property
     */
    public int getEstMarketValue() {
        return estMarketValue;
    }

    /**
     * Returns the location type for this property
     * @return the String with location category
     */
    public String getLocationCategory() {
        return locationCategory;
    }

    /**
     * Gets a String determining whether this property is a principal private residence of current owner
     * @return  the String with "YES" or "NO"
     * */
    public String getPrincipalPrivateRes() {
        return principalPrivateRes;
    }

    /**
     * Calculates the current tax that would be due this year for this property using TaxCalculator
     * @return  the double for tax due for this property
     */
    public double getCurrentTax() {
        TaxCalculator tc = new TaxCalculator(estMarketValue, locationCategory, principalPrivateRes);
        return tc.getTax();
    }

    /**
     * Overrides toString method from the Object class to display property
     * @return  the String of property data fields separated by commas
     */
    @Override
    public String toString() {
        return String.format("%s,%s,%d,%s,%s,%s", eircode, address, estMarketValue, locationCategory, principalPrivateRes, curOwnerId);
    }


}
