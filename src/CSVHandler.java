import java.io.*;
import java.util.Scanner;

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
        try(FileWriter output =  new FileWriter(properties, true)) {
            output.write(s);
        } catch (IOException ex) {
            System.out.println("Cannot write to properties.csv file.");
            throw ex;
        }
    }
    public void writeToTax(String s) throws IOException{
        try(FileWriter output =  new FileWriter(tax, true)) {
            output.write(s);
        } catch (IOException ex) {
            System.out.println("Cannot write to tax.csv file.");
            throw ex;
        }
    }
    public void writeToOwners(String s) throws IOException {
        try(FileWriter output = new FileWriter(owners, true) ) {
            output.write(s);
        } catch(IOException ex) {
            System.out.println("Cannot write to owners.csv file.");
            throw ex;
        }
    }
    public String readFromProperties(String eircode) throws IOException {
        try(Scanner input = new Scanner(properties)) {
            boolean found = false;
            String s1 = "";
            while(input.hasNextLine() && !found) {
                s1 = input.nextLine();
                if (s1.startsWith(eircode)) {
                    found = true;
                }
            }
            return (found) ? s1 : null;
        } catch(IOException ex) {
            System.out.println("Cannot access properties.csv.");
            throw ex;
        }
    }
    public String readFromTax(String eircode) throws IOException {
        try(Scanner input = new Scanner(tax)) {
            boolean found = false;
            String s1 = "";
            while(input.hasNextLine() && !found) {
                s1 = input.nextLine();
                if (s1.startsWith(eircode)) {
                    found = true;
                }
            }
            return (found) ? s1 : null;
        } catch(IOException ex) {
            System.out.println("Cannot access tax.csv.");
            throw ex;
        }
    }
    public String readFromOwners(int ownerId) throws IOException {
        try(Scanner input = new Scanner(owners)) {
            boolean found = false;
            String s1 = "";
            while(input.hasNextLine() && !found) {
                s1 = input.nextLine();
                if (s1.startsWith(String.valueOf(ownerId))) {
                    found = true;
                }
            }
            return (found) ? s1 : null;
        } catch(IOException ex) {
            System.out.println("Cannot access owners.csv.");
            throw ex;
        }
    }
}
