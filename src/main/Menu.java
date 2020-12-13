package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

/**
 * The Menu is a command line user interface system cooperating with PCMSystem class
 */
public class Menu {
        private Scanner in;
        public Menu() {
            in = new Scanner(System.in);
        }

    /**
     * Runs the CLI menu
     */
    public void run() {
            boolean more = true;

            System.out.println("Property Charge Management System, version 0.1");
            System.out.println("Login as \nO)wner of a property\nM)anager from Department of Environment\nE)xit");
            String letter = in.nextLine().toUpperCase();
            while (more) {
                if (letter.equals("O")) {
                    CSVHandler csv = new CSVHandler();
                    boolean flag = true;

                    HashMap<String, String> users = new HashMap<>();
                    users.put("12345AB", "pass321");
                    String ownerId = "";
                    while (flag) {
                        System.out.println("Enter your owner id: ");
                        ownerId = in.nextLine().toUpperCase();
                        if (users.containsKey(ownerId)) {
                            System.out.println("Enter your password: ");
                            String password = in.nextLine();
                            if (users.get(ownerId).equals(password)) {
                                flag = false;
                            } else {
                                System.out.println("Wrong password. Please try again.");
                            }
                        } else {
                            System.out.println("Wrong username. Please try again.");
                        }
                    }
                    Owner owner = new Owner(ownerId);
                    boolean moreActions = true;
                    String line;
                    while (moreActions) {
                        System.out.println("R)egister a property\nL)ist my properties\nC)heck tax due\nP)ay tax\nG)et balancing statement\nV)iew payment history\nE)xit");
                        letter = in.nextLine().toUpperCase();
                        if (letter.equals("R")) {
                            System.out.println("Enter property Eircode: ");
                            String eircode = in.nextLine();
                            System.out.println("Enter property Address: ");
                            String address = in.nextLine();
                            System.out.println("Enter estimated market value of your property: ");
                            String estMarketValue = in.nextLine();
                            String[] options = {"City", "Large town", "Small town", "Village", "Countryside"};
                            displayChoices(options);
                            System.out.println("Select location category: ");
                            String l = in.nextLine().toUpperCase();
                            String locationCat = "";
                            if(l.equals("A") ){
                                locationCat = options[0];
                            } else if (l.equals("B")) {
                                locationCat = options[1];
                            } else if (l.equals("C")) {
                                locationCat = options[2];
                            } else if(l.equals("D")) {
                                locationCat = options[3];
                            } else if(l.equals("E")) {
                                locationCat = options[4];
                            }
                            System.out.println("Is this your principal private residence? (yes/no): ");
                            String ppRes = in.nextLine().toUpperCase();
                            while (!(ppRes.equals("YES") || ppRes.equals("NO"))) {
                                System.out.println("Incorrect input: please answer with word yes or no.");
                                ppRes = in.nextLine().toUpperCase();
                            }
                            System.out.println("Are you sure all data given is correct and that you want to add this property to your account? (yes/no): ");
                            String ans = in.nextLine().toUpperCase();
                            if (ans.equals("YES")) {
                                System.out.println((owner.registerProperty(eircode, address, Integer.parseInt(estMarketValue), locationCat, ppRes)) ? "PropertyProperty added successfully" : "Error, try again later");
                            }
                        } else if (letter.equals("L")) {
                            owner.viewListOfProperties();
                        } else if(letter.equals("C")) {
                            owner.viewOverdueTax();
                        } else if (letter.equals("P")) {
                            System.out.println("Select property to pay the tax for: ");
                            ArrayList<Payment> taxToPay = owner.getOverdueTax();
                            String[] options = new String[owner.getOverdueTax().size()];
                            for(int i=0; i<taxToPay.size();i++) {
                                options[i] = taxToPay.get(i).toString();
                            }
                            displayChoices(options);
                            line = in.nextLine().toUpperCase();
                            owner.payTax(taxToPay.get(line.charAt(0)-'A'));
                        } else if (letter.equals("G")) {
                            System.out.println("Select year to get the balancing statement for: ");
                            int year = in.nextInt();
                            owner.getBalancingStatementFor(year);
                            in.nextLine();
                        } else if (letter.equals("E")) {
                            moreActions = false;
                        }

                    }

                } else if (letter.equals("M")) {
                    boolean flag = true;
                    HashMap<String, String> users = new HashMap<>();
                    users.put("Department", "Environment");
                    String Department = "";
                    while (flag) {
                        System.out.println("Enter your Security id: ");
                        Department = in.nextLine();
                        if (users.containsKey(Department)) {
                            System.out.println("Enter your password: ");
                            String password = in.nextLine();
                            if (users.get(Department).equals(password)) {
                                flag = false;
                            } else {
                                System.out.println("Wrong password. Please try again.");
                            }
                        } else {
                            System.out.println("Wrong username. Please try again.");
                        }
                    }
                    // Owner owner = new Owner(Department);
                    boolean moreActions = true;
                    String line;
                    while (moreActions) {
                        System.out.println("V)iew tax payment data\nF)ind overdue property tax\nT)ax in an Area\nE)xit");
                        letter = in.nextLine().toUpperCase();
                        if (letter.equals("V")) {
                            System.out.println("P)roperty tax Payments\nO)wner Tax Payments ");
                            letter = in.nextLine().toUpperCase();
                            if (letter.equals("P")) {
                                System.out.println("Enter property eircode: ");
                                line = in.nextLine().toUpperCase();
                                TaxManager.viewPaymentDataForProperty(line);
                            } else if (letter.equals("O")) {
                                System.out.println("Enter owner id: ");
                                line = in.nextLine().toUpperCase();
                                TaxManager.viewOwnerTaxPaymentData(line);
                            }
                        } else if (letter.equals("F")) {
                            System.out.println("A) By Year\nB) By Year and Eircode");
                            letter = in.nextLine().toUpperCase();
                            if(letter.equals("A")) {
                                System.out.println("Enter the year: ");
                                line = in.nextLine();
                                TaxManager.viewOverdueTaxForYear(Integer.parseInt(line));
                            } else if(letter.equals("B")) {
                                System.out.println("Enter the year: ");
                                line = in.nextLine();
                                System.out.println("Enter the eircode: ");
                                letter = in.nextLine().toUpperCase();
                                TaxManager.viewOverdueTaxForYearArea(Integer.parseInt(line), letter);
                            }
                        } else if (letter.equals("T")) {
                            String[] options = {"View total tax paid", "View average tax paid","View number and percentage of property taxes paid"};
                            displayChoices(options);
                            letter = in.nextLine().toUpperCase();
                            System.out.println("Enter area routing key: ");
                            line = in.nextLine().toUpperCase();
                            if(letter.equals("A")) {
                                TaxManager.viewTotalTaxPaidPerArea(line);
                            } else if (letter.equals("B")) {
                                TaxManager.viewAverageTaxPaidPerArea(line);
                            } else if(letter.equals("C")) {
                                TaxManager.viewNumberAndPercentageOfTaxPaidPerArea(line);
                            }
                        } else if(letter.equals("E")) {
                            moreActions = false;
                        }
                    }

                } else if (letter.equals("E")) {
                    more = false;
                }
            }
        }

        private void displayChoices(String[] options) {
            char c = 'A';
            for (String o : options) {
                System.out.println(c + ") " + o);
                c++;
            }
        }

}
