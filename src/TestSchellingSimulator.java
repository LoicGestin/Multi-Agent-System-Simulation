import gui.GUISimulator;

import java.awt.*;

public class TestSchellingSimulator  {
    public static void main(String[] args) {
        int k = 4;
        int nombre_famille = 2;
        Color[] colors= new Color[nombre_famille];
        colors[0] = Color.BLUE;
        colors[1] = Color.RED;
        //colors[2] = Color.yellow;

        int[] proba = new int[nombre_famille];
        proba[0] = 35;
        proba[1] = 35;
        //proba[2]= 20;
        Schelling schelling = new Schelling(30,30,k,nombre_famille,colors,proba);


        GUISimulator gui = new GUISimulator (750 , 750, Color.BLACK ) ;
        gui.setSimulable ( new GameSimulator(schelling, gui)) ;
    }
}
