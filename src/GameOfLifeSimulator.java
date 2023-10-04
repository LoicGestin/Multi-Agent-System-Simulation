import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;

public class GameOfLifeSimulator implements Simulable {
    private GameOfLife gameOfLife;
    private GUISimulator gui;

    public GameOfLifeSimulator(GameOfLife gameOfLife, GUISimulator gui){
        this.gameOfLife = gameOfLife;
        this.gui = gui;
        this.draw();
    }

    @Override
    public void next() {
        this.gameOfLife.update();
        this.draw();
    }

    @Override
    public void restart() {
        this.gameOfLife.reInit();
        this.draw();
    }


    public void draw(){
        gui.reset();
        int[][] grid = this.gameOfLife.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                gui.addGraphicalElement(new gui.Rectangle(25 +j * (this.gui.getPanelWidth() / this.gameOfLife.getM()) ,25 + i * (this.gui.getPanelHeight() / this.gameOfLife.getM()) , Color.GRAY, grid[i][j] == 0 ? Color.white : Color.black,this.gui.getPanelHeight() / this.gameOfLife.getM()));
            }
        }



    }

    public static void main(String[] args) {

        GameOfLife gameOfLife = new GameOfLife(20,20);
        /*
        gameOfLife.make_alive(3,1);
        gameOfLife.make_alive(2,2);
        gameOfLife.make_alive(2,3);
        gameOfLife.make_alive(1,2);
        */

        gameOfLife.make_alive(1,1);
        gameOfLife.make_alive(1,2);
        gameOfLife.make_alive(1,3);
        gameOfLife.make_alive(0,2);


        gameOfLife.make_alive(7,8);
        gameOfLife.make_alive(8,9);
        gameOfLife.make_alive(9,7);
        gameOfLife.make_alive(9,8);
        gameOfLife.make_alive(9,9);

        GUISimulator gui = new GUISimulator (500 , 500 , Color.BLACK ) ;
        gui.setSimulable ( new GameOfLifeSimulator(gameOfLife, gui)) ;
    }
}
