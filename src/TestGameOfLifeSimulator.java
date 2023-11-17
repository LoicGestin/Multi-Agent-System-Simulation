import gui.GUISimulator;

import java.awt.*;

public class TestGameOfLifeSimulator {
    public static void main(String[] args) {

        GameOfLife gameOfLife = new GameOfLife(30, 30);
        /*
        gameOfLife.make_alive(3,1);
        gameOfLife.make_alive(2,2);
        gameOfLife.make_alive(2,3);
        gameOfLife.make_alive(1,2);
        */

        gameOfLife.make_alive(5, 5, 1);
        gameOfLife.make_alive(5, 6, 1);
        gameOfLife.make_alive(5, 7, 1);
        gameOfLife.make_alive(4, 6, 1);


        gameOfLife.make_alive(7, 8, 1);
        gameOfLife.make_alive(8, 9, 1);
        gameOfLife.make_alive(9, 7, 1);
        gameOfLife.make_alive(9, 8, 1);
        gameOfLife.make_alive(9, 9, 1);

        GUISimulator gui = new GUISimulator(750, 750, Color.BLACK);
        gui.setSimulable(new GameSimulator(gameOfLife, gui));
    }
}
