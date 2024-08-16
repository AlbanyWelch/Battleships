package Battleships;

import java.util.*;

public class BSCLI {

    protected BSModel model;

    public BSCLI(BSModel m) {
        this.model = m;
        gameLoop();
    }

    // prints the new interface each turn
    public void printInterface() {
        String topLabels = "     A  B  C  D  E  F  G  H  I  J \n";
        String grid = "";
        grid += topLabels;
        for (int i = 0; i < 10; i++) {
            if (i == 9) {
                grid += " " + (i + 1) + " ";
            } else {
                grid += " " + (i + 1) + "  ";
            }
            for (int j = 0; j < 10; j++) {
                if (!model.Cells[i][j].text.equals("")) {
                    grid += " " + model.Cells[i][j].text + " ";
                } else {
                    grid += " x ";
                }
            }
            grid += "\n";
        }
        System.out.print(grid);
    }

    // prints the stats of the game
    public void printStats() {
        String stats = "Turns: " + model.turns + " Ships: " + model.shipsRemain + " Hits: " + model.hits;
        System.out.println(stats);
    }

    // Main gameloop - will repeat until game is over
    public void gameLoop() {
        printInterface();
        printStats();

        Scanner input = new Scanner(System.in);
        System.out.println("Enter Coordinates (E.g. A1): ");
        String[] option = input.next().split("");

        // if option includes number 10
        if (option.length == 3) {
            option[1] = option[1] + option[2];
        }
        int[] o = changeCoor(option);

        if (o[0] != -1) {
            model.change(o[0], o[1]);
            if (model.win) {
                input.close();
                System.out.println("You've won!");
                System.out.println("Number of turns taken: " + model.turns);
            } else {
                gameLoop();
            }
        } else {
            System.out.println("Cannot understand Coordinate.");
            gameLoop();
        }
    }

    // Used for changing user input to coordinates that the game understands
    public int[] changeCoor(String[] option) {
        List<String> l = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J");
        String letter = option[0];
        String num = option[1];

        int i;
        if (l.contains(letter)) {
            i = l.indexOf(letter);
        } else {
            i = -1;
        }
        int j = Integer.parseInt(num) - 1;

        int[] coor = new int[2];
        coor[1] = i;
        coor[0] = j;

        return coor;
    }
}
