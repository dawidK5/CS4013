package main;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * main.CSVHandler is class for reading from and writing to the csv files.
 */
public class CSVHandler {
    private File properties; // eircode,address,currentMarketValue,locationType,principalResidence, currentOwnerId
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
    public ArrayList<String> readFromTax(int year) {
        ArrayList<String> lines = new ArrayList<>();
        try(Scanner input = new Scanner(tax)) {
            String s1 = "";
            String sYear = String.format("%d,0",year);
            while(input.hasNextLine()) {
                s1 = input.nextLine();
                if (s1.contains(sYear)) {
                    String eirc = s1.substring(0, s1.indexOf(','));
                    eirc +=
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
    public ArrayList<String> readFromTax(int year, String routingKey) {
        ArrayList<String> lines = new ArrayList<>();
        try(Scanner input = new Scanner(tax)) {
            String s1 = "";
            String sYear = String.format("%d,0",year);
            while(input.hasNextLine()) {
                s1 = input.nextLine();
                if(s1.startsWith(routingKey)) {
                    if (s1.contains(sYear)) {
                        String eirc = s1.substring(0, s1.indexOf(','));
                        eirc += lines.add(s1);
                    }
                } else {
                    continue;
                }
            }
            return lines;
        } catch(IOException ex) {
            System.out.println("Cannot access tax.csv.");
            System.exit(2);
        }
        return null;
    }
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
    public ArrayList<String> readTaxesDueFromTax(main.TaxCalculator tc) {
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

    public void changeTaxPaymentStatus(String eircode, int year, double nAmount) {
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
