package Battleships;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BSView implements Observer, ActionListener {

    private static final Dimension panelSize = new Dimension(600, 600);

    protected BSModel model;
    protected BSController controller;
    private JFrame frame;
    private JPanel gridPanel;
    private JPanel statsPanel;
    private JPanel gridIndexTop;
    private JPanel gridIndexSide;

    private JLabel winMessage = new JLabel("Game Over!");

    private JLabel turnsLabel = new JLabel("Turns:");
    private JLabel hitsLabel = new JLabel("Hits:");
    private JLabel shipsLabel = new JLabel("Ships:");

    // private final JButton newGame = new JButton("New Game");

    public BSView(BSModel m, BSController c) {
        this.model = m;
        model.addObserver(this);
        this.controller = c;
        createPanel();
        controller.setView(this);
        update(model, null);
    }

    public void createPanel() {
        // Frame
        frame = new JFrame("Battleships");
        frame.setSize(panelSize);

        // Top Panel
        gridIndexTop = new JPanel();
        gridIndexTop.setLayout(new GridLayout(1, 11));
        gridIndexTop.add(new JLabel());
        for (int i = 0; i < 10; i++) {
            gridIndexTop.add(new JLabel(Character.toString((char) (('A' + i)))));
        }

        // Side Panel
        gridIndexSide = new JPanel();
        gridIndexSide.setLayout(new GridLayout(10, 1));
        for (int i = 0; i < 10; i++) {
            gridIndexSide.add(new JLabel(Integer.toString(i + 1)));
        }

        // Grid Panel
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(10, 10));
        for (int i = 0; i < 10; i++) {
            for (int x = 0; x < 10; x++) {
                Cell c = model.Cells[i][x];
                gridPanel.add(c);
                c.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        controller.change(c.getRow(), c.getCol());
                        c.setEnabled(false);
                        if (c.text == "H") {
                            c.setBackground(Color.RED);
                        } else if (c.text == "M") {
                            c.setBackground(Color.BLUE);
                        }
                    }
                });
            }
        }

        // Stats Panel
        statsPanel = new JPanel();
        statsPanel.add(turnsLabel, BorderLayout.WEST);
        statsPanel.add(hitsLabel, BorderLayout.WEST);
        statsPanel.add(shipsLabel, BorderLayout.WEST);

        JButton newGame = new JButton("New Game");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        statsPanel.add(newGame, BorderLayout.EAST);

        // Place panels onto frame
        frame.add(gridIndexTop, BorderLayout.NORTH);
        frame.add(gridIndexSide, BorderLayout.WEST);
        // frame.setContentPane(gridPanel);
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(statsPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

    }

    @Override
    public void update(Observable o, Object arg) {
        if (model.win) {
            statsPanel.add(winMessage, BorderLayout.EAST);
            turnsLabel.setText("Turns:" + Integer.toString(model.turns));
            hitsLabel.setText("Hits:" + Integer.toString(model.hits));
            shipsLabel.setText("Ships:" + Integer.toString(model.shipsRemain));
            frame.repaint();
            gridPanel.disable();
        } else {
            turnsLabel.setText("Turns:" + Integer.toString(model.turns));
            hitsLabel.setText("Hits:" + Integer.toString(model.hits));
            shipsLabel.setText("Ships:" + Integer.toString(model.shipsRemain));
            frame.repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }
}
