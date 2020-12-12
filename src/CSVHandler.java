import java.io.*;
import java.util.Scanner;

public class CSVHandler {
    private File properties; // eircode,address,currentMarketValue,locationType,principalResidence, currentOwnerId
    private File tax;       // eircode,ownerId,year,amount,paid/not paid
    private File owners;    // ownerId,name,surname,eircodes_array[all ever-owned properties]

    public CSVHandler() {
        // if (!properties.exists())
        try {
            properties = new File("properties.csv");
            properties.createNewFile();
            tax = new File("tax.csv");
            tax.createNewFile();
            owners = new File("owners.csv");
            owners.createNewFile();
        } catch (IOException ex) {
            System.out.println("Cannot create csv file.");
            System.exit(1);
        }
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
    public String readFromProperties(String eircode) {
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
            System.exit(2);
        }
        return null;
    }
    public String readFromTax(String eircode) {
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
            System.exit(2);
        }
        return null;
    }
    public String readFromOwners(String ownerId) {
        try(Scanner input = new Scanner(owners)) {
            boolean found = false;
            String s1 = "";
            while(input.hasNextLine() && !found) {
                s1 = input.nextLine();
                if (s1.startsWith(ownerId)) {
                    found = true;
                }
            }
            return (found) ? s1 : null;
        } catch(IOException ex) {
            System.out.println("Cannot access owners.csv.");
            System.exit(2);
        }
        return null;
    }

    protected void removeLine(String fileType, String line) {
        Scanner input;
        File source;
        File temp;
        if (fileType.equals("property")) {
            source = properties;
        } else if (fileType.equals("tax")) {
            source = tax;
        } else {
            source = owners;
        }

        try {
            input = new Scanner(source);
            temp = new File("temp_"+source.getName()+".csv");
            FileWriter output = new FileWriter(temp, true);
            while (input.hasNext()) {
                String s1 = input.nextLine();
                if (line.equals(s1)) {
                    continue;
                }
                output.write(s1);
            }
            input.close();
            output.close();

            temp.renameTo(new File(source.getName()));
            if (fileType.equals("property")) {
                properties = temp;
            } else if (fileType.equals("tax")) {
                tax = temp;
            } else {
                owners = temp;
            }
        } catch (IOException e) {
                System.out.println("Cannot remove line from a csv file.");
                System.exit(2);
            }
        }
    }
