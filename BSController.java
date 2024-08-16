package Battleships;

public class BSController {
    BSModel model;
    BSView view;

    public BSController(BSModel m) {
        this.model = m;
    }

    public void setView(BSView view) {
        this.view = view;
    }

    public void change(int r, int c) {
        model.change(c, r);
    }

    public void initialise() {
        model.initialise("n");
    }

}