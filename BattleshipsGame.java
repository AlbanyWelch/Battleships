package Battleships;

import java.util.*;

public class BattleshipsGame {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        // Asks user for option
                        System.out.println("Welcome to Battleships!");
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Game-mode (GUI or CLI): ");
                        String option = scan.nextLine();
                        System.out.println("Load File Configuration (y/n): ");
                        String config = scan.next();
                        if (option.equals("GUI")) {
                            createAndShowGUI(config); // GUI Version
                        } else if (option.equals("CLI")) {
                            createCLI(config); // CLI Version
                        } else {
                            System.out.println("Option not available; please try again");
                            run(); // Retry Option
                        }
                    }
                });
    }

    public static void createAndShowGUI(String config) {
        BSModel model = new BSModel(config);
        BSController controller = new BSController(model);
        BSView view = new BSView(model, controller);
    }

    public static void createCLI(String config) {
        BSModel model = new BSModel(config);
        BSCLI cli = new BSCLI(model);
    }
}
