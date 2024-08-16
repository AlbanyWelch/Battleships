package Battleships;

import javax.swing.JButton;

public class Cell extends JButton {
    private final int col;
    private final int row;
    private boolean state;
    protected String text;

    public Cell(int c, int r) {
        this.col = c;
        this.row = r;
        this.state = false;
        this.text = "";
    }

    public int getCol() {
        return this.col;
    }

    public int getRow() {
        return this.row;
    }

    public String getText() {
        return this.text;
    }

    public Boolean getState() {
        return this.state;
    }

    public void changeState() {
        this.state = true;
    }

    public void changeText(boolean hit) {
        if (hit) {
            this.text = "H";
        } else {
            this.text = "M";
        }
    }
}
