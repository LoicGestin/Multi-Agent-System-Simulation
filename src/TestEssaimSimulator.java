import gui.GUISimulator;

import java.awt.*;

public class TestEssaimSimulator {
    public static void main(String[] args) {
        GroupeEssaim groupeEssaim = new GroupeEssaim();

        Essaim essaim = new Sardines(900,100,900,100);
        essaim.addBoid(new Vector(200,200),new Vector(4,6));
        essaim.addBoid(new Vector(150,100),new Vector(1,2));
        essaim.addBoid(new Vector(300,150),new Vector(5,1));
        essaim.addBoid(new Vector(200,500),new Vector(5,-5));

        essaim.addBoid(new Vector(500,500),new Vector(-5,-5));
        essaim.addBoid(new Vector(800,800),new Vector(-4,5));

        essaim.addBoid(new Vector(700,200),new Vector(4,6));
        essaim.addBoid(new Vector(150,800),new Vector(1,-4));
        essaim.addBoid(new Vector(700,550),new Vector(5,-1));

        Essaim essaim2 = new Saumons(900,100,900,100);
        essaim2.addBoid(new Vector(250,220),new Vector(1,5));
        essaim2.addBoid(new Vector(500,130),new Vector(3,5));
        essaim2.addBoid(new Vector(320,280),new Vector(-6,2));
        essaim2.addBoid(new Vector(450,620),new Vector(-2,5));

        essaim2.addBoid(new Vector(260,430),new Vector(-2,5));
        essaim2.addBoid(new Vector(780,535),new Vector(4,-5));

        essaim2.addBoid(new Vector(652,348),new Vector(6,3));
        essaim2.addBoid(new Vector(345,712),new Vector(5,-2));
        essaim2.addBoid(new Vector(621,732),new Vector(-5,-1));


        Essaim essaim3 = new Requins(900,100,900,100);
        essaim3.addBoid(new Vector(300,300),new Vector(5,5));
        essaim3.addBoid(new Vector(500,700),new Vector(2,-2));
        essaim3.addBoid(new Vector(500,280),new Vector(-5,2));
        essaim3.addBoid(new Vector(600,620),new Vector(-2,4));

        groupeEssaim.addEsaim(essaim);
        groupeEssaim.addEsaim(essaim2);
        groupeEssaim.addEsaim(essaim3);

        GUISimulator gui = new GUISimulator (1000 , 1000 , Color.BLACK ) ;
        gui.setSimulable ( new GameSimulator(groupeEssaim, gui)) ;
    }


}
