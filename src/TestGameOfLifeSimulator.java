import gui.GUISimulator;

import java.awt.*;

public class TestGameOfLifeSimulator {
    public static void main(String[] args) {

        GameOfLife gameOfLife = new GameOfLife(30, 30);
        /*
        gameOfLife.makeAlive(3,1);
        gameOfLife.makeAlive(2,2);
        gameOfLife.makeAlive(2,3);
        gameOfLife.makeAlive(1,2);
        */

        gameOfLife.makeAlive(5, 5, 1);
        gameOfLife.makeAlive(5, 6, 1);
        gameOfLife.makeAlive(5, 7, 1);
        gameOfLife.makeAlive(4, 6, 1);


        gameOfLife.makeAlive(7, 8, 1);
        gameOfLife.makeAlive(8, 9, 1);
        gameOfLife.makeAlive(9, 7, 1);
        gameOfLife.makeAlive(9, 8, 1);
        gameOfLife.makeAlive(9, 9, 1);

        GUISimulator gui = new GUISimulator(750, 750, Color.BLACK);
        gui.setSimulable(new GameSimulator(gameOfLife, gui));
    }
}
