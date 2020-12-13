package main;
import gui.*;


/**
 * PCMSystem decides between GUI and CLI menu to run
 */
public class PCMSystem {
    public static void main(String[] args) {
        //new Owner("12345AB").viewPaymentsMadeForAllProperties();
        String[] empty = new String[1];

        if(args.length > 0) {
            gui.Main.main(empty);
        } else {
            Menu m = new Menu();
            m.run();
        }

    }



}
