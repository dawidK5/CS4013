import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * The Menu is a command line user interface system cooperating with PCMSystem class
 */
public class Menu {
        private Scanner in;
        public Menu() {
            in = new Scanner(System.in);
        }

        public void run() {
            boolean more = true;

            System.out.println("Property Charge Management System, version 0.1");
            System.out.println("Login as \nO)wner of a property\nM)anager from Department of Environment\nQ)uit");
            String letter = in.nextLine().toUpperCase();
            while (more) {
                if (letter.equals("O")) {
                    boolean flag = true;

                    HashMap<String,String> users = new HashMap<>();
                    users.put("12345AB", "pass321");
                    String ownerId = "";
                    while(flag) {
                        System.out.println("Enter your owner id: ");
                        ownerId = in.nextLine().toUpperCase();
                        if(users.containsKey(ownerId)) {
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
                    while(moreActions) {
                        System.out.println("R)egister a property\nL)ist my properties\nP)ay tax\nG)et balancing statement\nV)iew payment history\nE)xit");
                        letter = in.nextLine().toUpperCase();
                        if(letter.equals("R")) {
                            System.out.println("Enter property Eircode: ");
                            String eircode = in.nextLine();
                            System.out.println("Enter property Address: ");
                            String address = in.nextLine();
                            System.out.println("Enter estimated market value of your property: ");
                            int estMarketValue = in.nextInt();
                            String[] options = {"City", "Large town", "Small town", "Village", "Countryside"};
                            displayChoices(options);
                            System.out.println("Select location category: ");
                            char index = in.nextLine().charAt(0);
                            String locationCategory = options[index - 'A'];
                            System.out.println("Is this your principal private residence? (yes/no): ");
                            String ppRes = in.nextLine().toUpperCase();
                            while (!(ppRes.equals("YES") || ppRes.equals("NO"))) {
                                System.out.println("Incorrect input: please answer with word yes or no.");
                                ppRes = in.nextLine().toUpperCase();
                            }
                            System.out.println("Are you sure all data given is correct and that you want to add this property to your account? (yes/no): ");
                            String ans = in.nextLine().toUpperCase();
                            if (ans.equals("YES")) {
                                // we should move the registerProperty method to more appropriate location e.g. PCMSystem
                                // as property should be added for a specific owner. Maybe we should have a list of owners
                                // and just read it into arraylist in PCMSystem?
                                System.out.println((owner.registerProperty(eircode, address, estMarketValue, locationCategory, ppRes)) ? "Property added successfully" : "Error, try again later");
                            }
                        } else if(letter.equals("L")) {
                            owner.viewListOfProperties();
                        } else if(letter.equals("P")) {
                            System.out.println("Select property to pay the tax for: ");
                            line = in.nextLine().toUpperCase();
                            // owner.payTax();
                        } else if(letter.equals("G")) {
                            System.out.println("Select year to get the balancing statement for: ");
                            int year = in.nextInt();
                            owner.getBalancingStatementFor(year);
                            in.nextLine();
                        } else if (letter.equals("E")) {
                            moreActions = false;
                        }

                    }

                } else if (letter.equals("C")) {

                }

                else if (letter.equals("Q")) {
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

        /*
        private Appointment getChoice(ArrayList<Appointment> choices)
        {
            if (choices.size() == 0) return null;
            while (true)
            {
                char c = 'A';
                for (Appointment choice : choices)
                {
                    System.out.println(c + ") " + choice.format());
                    c++;
                }
                String input = in.nextLine();
                int n = input.toUpperCase().charAt(0) - 'A';
                if (0 <= n && n < choices.size())
                    return choices.get(n);
            }
        }

         */


}
