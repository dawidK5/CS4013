package main;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * CSVHandler is class for reading from and writing to the csv files.
 */
public class CSVHandler {
    private File properties; // eircode,address,currentMarketValue,locationType,principalResidence,currentOwnerId
    private File tax;       // eircode,ownerId,year,amount,paid/not paid
    private File owners;    // ownerId,name,surname,eircodes_array[all ever-owned properties]

    /**
     * Class constructor. Opens 3 csv files for a system.
     */
    public CSVHandler() {
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

    /**
     * Writes into properties output file
     * @param s             the String line in format eircode,address,current market value,
     *                      location type,YES or NO for property being principal private residence
     * @throws IOException  if we can't write to the properties file
     */
    protected void writeToProperties(String s) throws IOException {
        try(FileWriter output =  new FileWriter(properties, true)) {
            output.write(s);
        } catch (IOException ex) {
            System.out.println("Cannot write to properties.csv file.");
            throw ex;
        }
    }

    /**
     * Adds a new tax entry for a property to the CSV files
     * @param s             the String in format "eircode,ownerId,year due,year paid,paid/notpaid"
     * @throws IOException  cannot write to tax.csv
     */
    protected void writeToTax(String s) throws IOException{
        try(FileWriter output =  new FileWriter(tax, true)) {
            output.write(s);
        } catch (IOException ex) {
            System.out.println("Cannot write to tax.csv file.");
            throw ex;
        }
    }

    /**
     * Adds a new entry to owners csv file
     * @param s             the String line in format "ownerId,first name,surname,
     *                      eircodes of properties (still owned or sold)"
     * @throws IOException  cannot write to owners.csv
     */
    protected void writeToOwners(String s) throws IOException {
        try(FileWriter output = new FileWriter(owners, true) ) {
            output.write(s);
        } catch(IOException ex) {
            System.out.println("Cannot write to owners.csv file.");
            throw ex;
        }
    }

    /**
     * Reads a csv entry about a property for eircode specified
     * @param eircode   the String for the eircode
     * @return          the property details in format
     *                  "eircode,address,property value,location category,
     *                  YES or NO (if property is principal residence),current owner's ID",
     *                  nul if eircode not found
     */
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

    /**
     * Reads a tax entry from csv file for a given eircode
     * @param eircode   The String for eircode
     * @return          the String for tax entries in format
     *                  "eircode,ownerId,year due,year paid,paid/notpaid,ownerId,year due,year paid,paid/notpaid...",
     *                  null if eircode not found
     */
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

    /**
     * Obtains a list of property eircodes that have tax overdue from a particular year
     * @param year      the integer for the year
     * @return          the ArrayList of Strings for tax overdue, each in format "owner id,year due,0,notpaid",
     *                  null if no such entries found
     */
    public ArrayList<String> readFromTax(int year) {
        ArrayList<String> lines = new ArrayList<>();
        try(Scanner input = new Scanner(tax)) {
            String s1 = "";
            String sYear = String.format("%d,0",year);
            while(input.hasNextLine()) {
                s1 = input.nextLine();
                if (s1.contains(sYear)) {
                    lines.add(s1);
                }
            }
            return lines;
        } catch(IOException ex) {
            System.out.println("Cannot access tax.csv.");
            System.exit(2);
        }
        return null;
    }

    /**
     * Queries the csv tax file to find the csv entries for properties with tax overdue in particular area
     * for year specified
     * @param year          the integer for the year to query
     * @param routingKey    the String of the routing key of the area to query
     * @return              the ArrayList of Strings with tax entries
     */
    public ArrayList<String> readFromTax(int year, String routingKey) {
        ArrayList<String> lines = new ArrayList<>();
        try(Scanner input = new Scanner(tax)) {
            String s1 = "";
            String sYear = String.format("%d,0",year);
            while(input.hasNextLine()) {
                s1 = input.nextLine();
                if(s1.startsWith(routingKey)) {
                    if (s1.contains(sYear)) {
                        lines.add(s1);
                    }
                }
            }
            return lines;
        } catch(IOException ex) {
            System.out.println("Cannot access tax.csv.");
            System.exit(2);
        }
        return null;
    }

    /**
     * Queries the csv tax file to find the total tax paid for particular area nased on routing key
     * @param routingKey    the String of the routing key
     * @return              the double with the sum of tax paid
     */
    public double readTotalFromTax(String routingKey) {
        double sum = 0;
        try(Scanner input = new Scanner(tax)) {
            String s1 = "";
            while (input.hasNextLine()) {
                s1 = input.nextLine();
                if (s1.startsWith(routingKey)) {
                    String[] values = s1.split(",");
                    for(int i=values.length-1; i>=4; i-=5) {
                        if(values[i].equals("paid")) {
                            sum+= Double.parseDouble(values[i-1]);
                        }
                    }

                }
            }
            return sum;
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find tax.csv.");
            System.exit(2);
        }
        return 0;
    }

    /**
     * Finds the average value of tax paid in a particular area
     * @param routingKey    the String for the routing key
     * @return              double for the average tax paid, 0 if no tax paid entries exists for the area
     */
    public double readAverageFromTax(String routingKey) {
        int count=0;
        double sum = 0;
        try(Scanner input = new Scanner(tax)) {
            String s1 = "";
            while (input.hasNextLine()) {
                s1 = input.nextLine();
                if (s1.startsWith(routingKey)) {
                    String[] values = s1.split(",");
                    for(int i=values.length-1; i>=4; i-=5) {
                        if(values[i].equals("paid")) {
                            count++;
                            sum+= Double.parseDouble(values[i-1]);
                        }
                    }

                }
            }
            return (count != 0) ? sum/(double)count : 0;
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find tax.csv.");
            System.exit(2);
        }
        return 0;
    }

    /**
     * Obtains the number of properties with tax paid in area specified by routing key and the percentage of taxes paid
     * @param routingKey    the String with the routing key for the area to query
     * @return              String in format "number of tax payments,percentage of taxes paid", "0.0,-1.0" if no
     *                      tax entries found
     */
    public String readNumAndProcFromTax(String routingKey) {
        int paid=0;
        int paidAndUnpaid=0;
        try(Scanner input = new Scanner(tax)) {
            String s1 = "";
            while (input.hasNextLine()) {
                s1 = input.nextLine();
                if (s1.startsWith(routingKey)) {
                    String[] values = s1.split(",");
                    for(int i=values.length-1; i>=4; i-=5) {
                        if(values[i].equals("paid")) {
                            paid++;
                        }
                        paidAndUnpaid++;
                    }
                }
            }
            return String.format("%d,%.2f",paid,paidAndUnpaid > 0 ? (paid/(double)paidAndUnpaid*100): -1.0);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find tax.csv.");
            System.exit(2);
        }
        return null;
    }

/*
    public ArrayList<String> readTaxesDueFromTax(TaxCalculator tc) {
        double sum = 0;
        try (Scanner input = new Scanner(tax)) {

            String s1 = "";
            while (input.hasNextLine()) {
                s1 = input.nextLine();

                String[] values = s1.split(",");
                int year = LocalDate.now().getYear();

                for (int i = values.length - 1; i >= 4; i -= 5) {
                    if (values[i].equals("notpaid")) {
                        tc.reload(readFromProperties(values[0]));
                        sum += tc.getTax();
                    }
                }
            }
        }
    }

 */

    /**
     * Reads an entry in a csv file for the owner specified
     * @param ownerId   the String for owner ID
     * @return          the owner details in format "ownerId,first name,surname,
     *                  eircodes of properties (still owned or sold)"
     */
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

    /**
     * Updates the csv file for tax to mark the tax as paid in this year
     * @param eircode   the String for the eircode
     * @param year      the integer for the year tax payment was due
     * @param nAmount   the double to be updated for the amount paid for the tax including any late fees
     */
    protected void changeTaxPaymentStatus(String eircode, int year, double nAmount) {
        try {
            Scanner input = new Scanner(tax);
            String sYear = Integer.toString((year%((year/100)*100))); // last 2 digits of year
            File temp = new File("temp_pay_tax.tmp");
            FileWriter output = new FileWriter(temp, true);
            while (input.hasNext()) {
                String s1 = input.nextLine();
                if (s1.startsWith(eircode)) {
                    String s2 = s1.substring(s1.indexOf(sYear+",0", s1.indexOf('d')));
                    output.write(s1.replaceFirst(s2, s2.replaceFirst(",0", ","+LocalDate.now().getYear())));
                    break;
                }
                output.write(s1);
            }
            while(input.hasNext())
                output.write(input.nextLine());
            input.close();
            output.close();
            temp.renameTo(new File(tax.getName()));
        } catch (FileNotFoundException e) {
            System.out.println("CSV tax file not found.");
        } catch (IOException e) {
            System.out.println("CSV file IO exception.");
        }
    }



    /*
    public void replaceLine(String fileType, String original, String replacement) {
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
        temp = new File("temp_"+source.getName()+".csv");
        try {
            FileWriter output = new FileWriter(temp, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     */

    /**
     * Removes the specified line from the csv file specified
     * @param fileType  the String for file type to modify, either "properties", "tax" or "owners"
     * @param line      the String for line to be removed from the csv file
     */
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
