import gui.GUISimulator;

import java.awt.*;

public class TestSchellingSimulator {
    public static void main(String[] args) {
        int k = 4;
        int nombreFamille = 2;
        Color[] colors = new Color[nombreFamille];
        colors[0] = Color.BLUE;
        colors[1] = Color.RED;
        //colors[2] = Color.yellow;

        int[] proba = new int[nombreFamille];
        proba[0] = 35;
        proba[1] = 35;
        //proba[2]= 20;
        Schelling schelling = new Schelling(30, 30, k, nombreFamille, colors, proba);


        GUISimulator gui = new GUISimulator(750, 750, Color.BLACK);
        gui.setSimulable(new GameSimulator(schelling, gui));
    }
}
