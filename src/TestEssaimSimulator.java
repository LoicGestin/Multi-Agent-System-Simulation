import gui.GUISimulator;

import java.awt.*;

public class TestEssaimSimulator {
    public static void main(String[] args) {
        Essaim essaim = new Essaim(150,30,20, 900,100,900,100);
        essaim.addBoid(new Vector(200,200),new Vector(4,6));
        essaim.addBoid(new Vector(150,100),new Vector(1,2));
        essaim.addBoid(new Vector(300,150),new Vector(5,1));
        essaim.addBoid(new Vector(200,500),new Vector(5,-5));

        essaim.addBoid(new Vector(500,500),new Vector(-5,-5));
        essaim.addBoid(new Vector(800,800),new Vector(-4,5));

        essaim.addBoid(new Vector(700,200),new Vector(4,6));
        essaim.addBoid(new Vector(150,800),new Vector(1,-4));
        essaim.addBoid(new Vector(700,550),new Vector(5,-1));
        GUISimulator gui = new GUISimulator (1000 , 1000 , Color.BLACK ) ;
        gui.setSimulable ( new GameSimulator(essaim, gui)) ;
    }


}
