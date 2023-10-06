import gui.GUISimulator;

import java.awt.*;

public class TestBallsSimulator {
    public static void main(String[] args) {





        Balls balls = new Balls();
        balls.add_ball(new Point(50,300));
        balls.add_ball(new Point(75,45));
        balls.add_ball(new Point(200,500));
        balls.add_ball(new Point(200,200));
        balls.add_ball(new Point(400,0));


        GUISimulator gui = new GUISimulator (500 , 600 , Color.BLACK ) ;
        gui.setSimulable ( new BallsSimulator(balls, gui)) ;



    }
}
