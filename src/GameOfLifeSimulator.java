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
                gui.addGraphicalElement(new gui.Rectangle(25 +j * (this.gui.getPanelWidth() / this.gameOfLife.getM()) ,25 + i * (this.gui.getPanelHeight() / this.gameOfLife.getM()) , Color.GRAY, this.gameOfLife.getColor(i,j), this.gui.getPanelHeight() / this.gameOfLife.getM()));
            }
        }



    }
}
