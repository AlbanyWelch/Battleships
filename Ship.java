package Battleships;

import java.util.ArrayList;
import java.util.List;

public class Ship {

    private final int length;
    private boolean active;
    private final int id;
    private ArrayList<ArrayList<Integer>> coordinates;
    private int numHits;

    public Ship(int l, int id) {
        this.id = id;
        this.length = l;
        this.active = true;
        this.numHits = 0;
        this.coordinates = new ArrayList<ArrayList<Integer>>(this.length);
    }

    public int getLength() {
        return this.length;
    }

    protected boolean checkActive() {
        return this.active;
    }

    protected void setCoordinates(ArrayList<Integer> c) {
        this.coordinates.add(c);
    }

    protected Boolean checkHit(int row, int col) {
        for (ArrayList<Integer> coord : coordinates) {
            if (coord.get(0) == row && coord.get(1) == col) {
                numHits += 1;
                if (numHits == length) {
                    this.active = false;
                }
                return true; // Found the coordinates
            }
        }
        return false; // Coordinates not found
    }
}
