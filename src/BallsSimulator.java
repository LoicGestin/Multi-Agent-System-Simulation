import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

import java.awt.*;
import java.util.Random;

public class BallsSimulator implements Simulable {
    private Balls balls;

    private GUISimulator gui;
    public BallsSimulator(Balls balls,GUISimulator gui){
        this.gui = gui;
        this.balls = balls;
        this.draw();


    }
    @Override
    public void next() {
        this.balls.translate(10,10,500,500);
        this.draw();

    }

    @Override
    public void restart() {
        this.balls.reInit();
        this.draw();
    }
    public void draw(){
        gui.reset();

        for (int i = 0; i < this.balls.getBalls().size(); i++) {
            gui.addGraphicalElement(new Oval(
                    this.balls.getBall(i).x,
                    this.balls.getBall(i).y,
                    Color.BLUE,
                    Color.RED,
                    20
            ));
        }
    }



}