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
        int randX = (int) (Math.random() * (20));
        int randY = (int) (Math.random() * (20));
        this.balls.translate(10,10,gui.getPanelWidth(),gui.getPanelHeight());
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


    public static void main(String[] args) {


        Balls balls = new Balls();
        balls.add_ball(new Point(50,96));
        balls.add_ball(new Point(75,45));
        balls.add_ball(new Point(200,52));


        GUISimulator gui = new GUISimulator (500 , 600 , Color.BLACK ) ;
        gui.setSimulable ( new BallsSimulator(balls, gui)) ;


    }
}