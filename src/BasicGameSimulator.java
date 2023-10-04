import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;

public class BasicGameSimulator implements Simulable {
    private BasicGame basicGame;
    private GUISimulator gui;

    public BasicGameSimulator(BasicGame basicGame, GUISimulator gui){
        this.basicGame = basicGame;
        this.gui = gui;
        this.draw();
    }

    @Override
    public void next() {
        this.basicGame.update();
        this.draw();
    }

    @Override
    public void restart() {
        this.basicGame.reInit();
        this.draw();
    }


    public void draw(){
        gui.reset();
        int[][] grid = this.basicGame.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                gui.addGraphicalElement(new gui.Rectangle(
                        25 +j * (this.gui.getPanelWidth() / this.basicGame.getM()),
                        25 + i * (this.gui.getPanelHeight() / this.basicGame.getM()),
                        Color.GRAY,
                        this.basicGame.getColor(i,j),
                        this.gui.getPanelHeight() / this.basicGame.getM()));
            }
        }



    }
}
