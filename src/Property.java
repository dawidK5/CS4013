public class Property {
    // owner(s)?
    private String eircode;
    private String address;
    private int estMarketValue;
    private String locationCategory;
    private String principalPrivateRes;
    
    public Property(String eircode, String address, int estMarketValue, String locationCategory, String principalPrivateRes) {
        this.eircode = eircode;
        this.address = address;
        this.estMarketValue = estMarketValue;
        this.locationCategory = locationCategory;
        this.principalPrivateRes = principalPrivateRes;
    }

    public String getEircode() {
        return eircode;
    }

    public String getAddress() {
        return address;
    }

    public int getEstMarketValue() {
        return estMarketValue;
    }

    public String getLocationCategory() {
        return locationCategory;
    }

    public String getPrincipalPrivateRes() {
        return principalPrivateRes;
    }
    public double getCurrentTax() {
        TaxCalculator tc = new TaxCalculator(estMarketValue, locationCategory, principalPrivateRes);
        tc.fees();
        tc.propertyTax();
        tc.livingAreaTax();
        tc.principalResFees();
        return tc.getTax();
    }

}
