import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CSVHandler {
    private File properties; // eircode,currentMarketValue,locationType,principalResidence
    private File tax;       // eircode,owner,year,amount,paid/not paid
    private File owners;    // name,surname,eircodes_array[currently owned properties]

    public CSVHandler() throws IOException {
        if(!properties.exists())
        properties = new File("properties.csv");
        if(!tax.exists())
        tax = new File("tax.csv");
        if(!owners.exists())
        owners = new File("owners.csv");
    }
    public void writeToProperties(String s) throws IOException {
        try(FileWriter out =  new FileWriter(properties)) {
            out.write(s);
        }
    }
    public void writeToTax(String s) {
        try(FileWriter out =  new FileWriter(properties)) {
            out.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
