package Battleships;

import java.util.*;
import java.util.regex.Pattern;
import java.io.*;

public class BSModel extends Observable {

    private Ship[] Ships;
    protected Cell[][] Cells;
    protected int turns; // number of turns player has left (set below)
    protected int hits; // number of successful hits
    protected int shipsRemain;
    protected boolean win;

    // for checking values each turn of the game
    private void invariant() {
        assert (hits >= 0 && hits <= 16); // 16 hits to take out all ships
        assert (turns >= 0 && turns <= 100); // 100 cells to choose -> max of 100 turns
        assert (Ships.length == 5); // 5 ships generated
        assert (Cells.length * Cells[0].length == 100); // 100 cells generated
        assert (shipsRemain >= 0 && shipsRemain <= 5);
    }

    // random initialisation of ships (no file configuration)
    private Ship[] setShips() {
        // Create New Ships (length, id)
        Ships[0] = new Ship(2, 1);
        Ships[1] = new Ship(2, 2);
        Ships[2] = new Ship(3, 3);
        Ships[3] = new Ship(4, 4);
        Ships[4] = new Ship(5, 5);

        // Random Coordinates Generation
        Random rand = new Random();

        for (int x = 0; x < 5; x++) {
            int l = Ships[x].getLength();
            int cellCol = rand.nextInt(0, 9);
            int cellRow = rand.nextInt(0, 9);
            // Random Orientation: true (horizontal), false (vertical)
            boolean orient = rand.nextBoolean();

            while (!isValid(cellRow, cellCol, orient, l)) {
                cellCol = rand.nextInt(0, 9);
                cellRow = rand.nextInt(0, 9);
                orient = rand.nextBoolean();
            }

            if (orient) {
                for (int j = 0; j < l; j++) {
                    ArrayList<Integer> c = new ArrayList<Integer>(2);
                    Cells[cellRow][cellCol + j].changeState();
                    c.add(cellRow);
                    c.add(cellCol + j);
                    Ships[x].setCoordinates(c);
                }
            } else {
                for (int j = 0; j < l; j++) {
                    ArrayList<Integer> c = new ArrayList<Integer>(2);
                    Cells[cellRow + j][cellCol].changeState();
                    c.add(cellRow + j);
                    c.add(cellCol);
                    Ships[x].setCoordinates(c);
                }
            }
        }
        return Ships;
    }

    // for checking ship placement, in case of intersetion
    private boolean isValid(int row, int col, boolean o, int l) {
        if (o) { // horizontal
            if (col + l > 10) {
                return false;
            }
            for (int j = 0; j < l; j++) {
                if (Cells[row][col + j].getState() == true) {
                    return false;
                }
            }
        } else { // vertical
            if (row + l > 10) {
                return false;
            }
            for (int j = 0; j < l; j++) {
                if (Cells[row + j][col].getState() == true) {
                    return false;
                }
            }
        }
        return true;
    }

    // create cells of grid
    private void setCells() {
        for (int i = 0; i < 10; i++) { // Rows
            for (int j = 0; j < 10; j++) { // Columns
                Cell c = new Cell(i, j);
                Cells[i][j] = c;
            }
        }
    }

    // called at every turn to check numbers and check valid move
    public void change(int r, int c) {
        invariant();
        checkMove(r, c);
        setChanged();
        notifyObservers();
    }

    // checks valid move and changes model accordingly
    public void checkMove(int r, int c) {
        if (Cells[r][c].text.equals("")) {
            turns += 1;
            if (Cells[r][c].getState() == true) {
                hits += 1;
                for (Ship s : Ships) {
                    if (s.checkHit(r, c)) {
                        if (!s.checkActive()) {
                            shipsRemain -= 1;
                        }
                    }
                }
                if (hits == 16) {
                    win = true;
                }
                Cells[r][c].changeText(true);
            } else {
                Cells[r][c].changeText(false);
            }
        }
    }

    // for file configuration
    private Ship[] configShip(String[] s) {
        Ships[Integer.parseInt(s[0]) - 1] = new Ship(s.length - 1, Integer.parseInt(s[0]));
        for (int i = 1; i < (s.length); i++) {
            String[] x = s[i].split(Pattern.quote("-"));
            ArrayList<Integer> c = new ArrayList<Integer>(2);
            c.add(Integer.parseInt(x[0]) - 1);
            c.add(Integer.parseInt(x[1]) - 1);

            Ships[Integer.parseInt(s[0]) - 1].setCoordinates(c);
            Cells[Integer.parseInt(x[0]) - 1][Integer.parseInt(x[1]) - 1].changeState();
        }
        return Ships;
    }

    // initialises game (file config or random placement of ships)
    public void initialise(String config) {
        setCells();
        if (config.equals("y")) {
            try {
                File file = new File("file.txt");
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    String[] s = reader.nextLine().split(Pattern.quote("."));
                    configShip(s);
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("Error - Reading File Configuration");
            }
        } else {
            this.Ships = setShips();
        }
        setChanged();
        notifyObservers();
    }

    // constructor
    public BSModel(String c) {
        this.Ships = new Ship[5];
        this.Cells = new Cell[10][10];
        this.turns = 0;
        this.hits = 0;
        this.shipsRemain = 5;
        this.win = false;
        initialise(c);
    }
}
