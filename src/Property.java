public class Property {
    // owner(s)?
    private String address;
    private String eircode;
    private int estMarketValue;
    private String locationCategory;
    private String principalPrivateRes;
    
    public Property(String address, String eircode, int estMarketValue, String locationCategory, String principalPrivateRes) {
        this.address = address;
        this.eircode = eircode;
        this.estMarketValue = estMarketValue;
        this.locationCategory = locationCategory;
        this.principalPrivateRes = principalPrivateRes;
    }
}
