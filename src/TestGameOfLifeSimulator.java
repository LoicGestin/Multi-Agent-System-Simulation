import gui.GUISimulator;

import java.awt.*;

public class TestGameOfLifeSimulator {
    public static void main(String[] args) {

        GameOfLife gameOfLife = new GameOfLife(20,20);
        /*
        gameOfLife.make_alive(3,1);
        gameOfLife.make_alive(2,2);
        gameOfLife.make_alive(2,3);
        gameOfLife.make_alive(1,2);
        */

        gameOfLife.make_alive(1,1,1);
        gameOfLife.make_alive(1,2,1);
        gameOfLife.make_alive(1,3,1);
        gameOfLife.make_alive(0,2,1);


        gameOfLife.make_alive(7,8,1);
        gameOfLife.make_alive(8,9,1);
        gameOfLife.make_alive(9,7,1);
        gameOfLife.make_alive(9,8,1);
        gameOfLife.make_alive(9,9,1);

        GUISimulator gui = new GUISimulator (500 , 500 , Color.BLACK ) ;
        gui.setSimulable ( new GameOfLifeSimulator(gameOfLife, gui)) ;
    }
}
