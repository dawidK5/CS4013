import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;
public class Menu {
        private Scanner in;
        public Menu() {
            in = new Scanner(System.in);
        }

        public void run() {
            boolean more = true;
            // AppointmentCalendar calendar = new AppointmentCalendar();
            System.out.println("Property Charge Management System, version 0.1");
            System.out.println("Login as \nO)wner of a property\nM)anager from Department of Environment\nQ)uit");
            String letter = in.nextLine().toUpperCase();
            while (more) {
                System.out.println("Enter your owner id: ");
                String ownerId = in.nextLine().toUpperCase();
                System.out.println("Enter your password: ");
                String password = in.nextLine();
                System.out.println("R)egister a property\nL)ist my properties\nP)ay tax\nG)et balancing statement\nE)xit");
                letter = in.nextLine().toUpperCase();
                if (letter.equals("O")) {
                    if(letter.equals("R")) {
                        System.out.println("Enter property Eircode: ");
                        String eircode = in.nextLine();
                        System.out.println("Enter property Address: ");
                        String address = in.nextLine();
                        System.out.println("Enter estimated market value of your property: ");
                        int estMarketValue = Integer.parseInt(in.nextLine());
                        String[] options = {"City", "Large town", "Small town", "Village", "Countryside"};
                        char c = 'A';
                        for(String o : options) {
                            System.out.println(c + ") "+o);
                            c++;
                        }
                        System.out.println("Select location category: ");
                        char index = in.nextLine().charAt(0);
                        String locationCategory = options[index-'A'];
                        System.out.println("Is this your principal private residence? (yes/no): ");
                        String ppRes = in.nextLine().toUpperCase();
                        while(!(ppRes.equals("YES") || ppRes.equals("NO"))) {
                            System.out.println("Incorrect input: please answer with word yes or no.");
                            ppRes = in.nextLine().toUpperCase();
                        }
                        System.out.println("Are you sure all data given is correct and that you want to add this property to your account? (yes/no): ");
                        String ans = in.nextLine().toUpperCase();
                        if(ans.equals("YES")) {
                            // we should move the registerProperty method to more appropriate location e.g. PCMSystem
                            // as property should be added for a specific owner. Maybe we should have a list of owners
                            // and just read it into arraylist in PCMSystem?
                            System.out.println((new Owner().registerProperty(eircode, address, estMarketValue, locationCategory, ppRes)) ? "Property added successfully" : "Error, try again later");

                        }

                    }

                }
                else if (letter.equals("C"))
                {
                    System.out.println("Enter Appointment Date");
                    String line = in.nextLine();
                    AppointmentDate day = new AppointmentDate(line);
                    Appointment a = getChoice(calendar.getAppointmentsForDay(day));
                    if (a != null)
                        calendar.cancel(a);
                }
                else if (letter.equals("S"))
                {
                    System.out.println("Date");
                    String line = in.nextLine();
                    AppointmentDate day = new AppointmentDate(line);
                    for (Appointment appt : calendar.getAppointmentsForDay(day))
                        System.out.println(appt.format());
                }
                else if (letter.equals("Q")) {
                    more = false;
                }
            }
        }

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


}
